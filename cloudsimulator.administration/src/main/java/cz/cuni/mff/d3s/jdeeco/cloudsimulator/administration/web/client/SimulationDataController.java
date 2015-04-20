package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.AppContext;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.data.models.SimulationData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.SimulationDataService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationDataItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.SimulationDataItemImpl;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.factories.SimulationDataItemFactory;

@Controller
public class SimulationDataController {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(SimulationDataController.class);

	private static final String DATA_VIEW = "main/data/simulationData";
	private static final String ADDDATA_VIEW = "main/data/addSimulationData";
	private static final String EDITDATA_VIEW = "main/data/editSimulationData";

	@Resource
	private SimulationDataService simulationDataService;

	@Resource
	private SimulationDataItemFactory simulationDataItemFactory;

	@Resource
	private SimulationDataValidator simulationDataValidator;

	@Resource
	private NavigationPathBuilder navigationPathBuilder;

	@Resource
	private AppContext appContext;
	

	@RequestMapping(value = MappingSettings.DATA)
	public ModelAndView showData(HttpServletRequest request, @PathVariable int dataId) {

		SimulationData data = simulationDataService.getDataById(dataId);

		if (data != null) {
			SimulationDataItem dataItem = getDataItem(data);

			ClientModelAndView modelAndView = getDefaultModelAnView(DATA_VIEW).withSimulationData(dataItem)
					.withNavigationPath(navigationPathBuilder.buildFromSimulationData(dataId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList(appContext.getSiteRoot());
	}

	@RequestMapping(value = MappingSettings.DATA_ADD, method = RequestMethod.GET)
	public ModelAndView addData(HttpServletRequest request, @PathVariable int projectId) {

		ClientModelAndView modelAndView = getDefaultModelAnView(ADDDATA_VIEW)
				.withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.PROJECT_ROOT, projectId))
				.withProjectId(projectId).withNavigationPath(navigationPathBuilder.buildFromProject(projectId));

		return modelAndView;
	}

	@RequestMapping(value = MappingSettings.DATA_ADD, method = RequestMethod.POST)
	public ModelAndView addData(HttpServletRequest request, @PathVariable int projectId,
			@ModelAttribute SimulationDataItemImpl simulationDataItem, BindingResult result) {

		simulationDataValidator.validate(simulationDataItem, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();

			ClientModelAndView modelAndView = getDefaultModelAnView(ADDDATA_VIEW)
					.withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.PROJECT_ROOT, projectId))
					.withProjectId(projectId).withSimulationData(simulationDataItem)
					.withErrorMessage(er.getDefaultMessage())
					.withNavigationPath(navigationPathBuilder.buildFromProject(projectId));

			return modelAndView;
		}

		simulationDataService.createData(projectId, simulationDataItem.getName(), simulationDataItem.getDescription(),
				simulationDataItem.getVcsType(), simulationDataItem.getRepositoryUrl(),
				simulationDataItem.getPomDirectory(), simulationDataItem.getMavenGoals());

		return ProjectController.RedirectToProject(appContext.getSiteRoot(), projectId);
	}

	@RequestMapping(value = MappingSettings.DATA_EDIT, method = RequestMethod.GET)
	public ModelAndView editData(HttpServletRequest request, @PathVariable int dataId) {

		SimulationData data = simulationDataService.getDataById(dataId);

		if (data != null) {
			SimulationDataItem dataItem = getDataItem(data);

			ModelAndView modelAndView = getDefaultModelAnView(EDITDATA_VIEW)
					.withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.DATA_ROOT, dataId))
					.withSimulationData(dataItem)
					.withNavigationPath(navigationPathBuilder.buildFromSimulationData(dataId));

			return modelAndView;
		}

		return ProjectController.RedirectToProjectList(appContext.getSiteRoot());
	}

	@RequestMapping(value = MappingSettings.DATA_EDIT, method = RequestMethod.POST)
	public ModelAndView editData(HttpServletRequest request, @PathVariable int dataId,
			@ModelAttribute SimulationDataItemImpl simulationDataItem, BindingResult result) {

		simulationDataValidator.validate(simulationDataItem, result);
		if (result.hasErrors()) {
			FieldError er = result.getFieldError();

			ClientModelAndView modelAndView = getDefaultModelAnView(EDITDATA_VIEW)
					.withCancelUri(MappingSettings.GetFullUri(appContext.getSiteRoot(), MappingSettings.DATA_ROOT, dataId))
					.withSimulationData(simulationDataItem).withErrorMessage(er.getDefaultMessage())
					.withNavigationPath(navigationPathBuilder.buildFromSimulationData(dataId));

			return modelAndView;
		}

		simulationDataService.editData(dataId, simulationDataItem.getName(), simulationDataItem.getDescription(),
				simulationDataItem.getVcsType(), simulationDataItem.getRepositoryUrl(),
				simulationDataItem.getPomDirectory(), simulationDataItem.getMavenGoals());

		return RedirectToData(dataId);
	}

	private SimulationDataItem getDataItem(SimulationData data) {
		return simulationDataItemFactory.create(data);
	}

	public ModelAndView RedirectToData(int dataId) {
		return RedirectToData(appContext.getSiteRoot(), dataId);
	}

	public static ModelAndView RedirectToData(String siteRoot, int dataId) {
		return new ModelAndView("redirect:" + MappingSettings.GetFullUri(siteRoot, MappingSettings.DATA_ROOT, dataId));
	}

	private ClientModelAndView getDefaultModelAnView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
