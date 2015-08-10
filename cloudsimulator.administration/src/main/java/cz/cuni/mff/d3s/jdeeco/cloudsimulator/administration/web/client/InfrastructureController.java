package cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.infrastructure.WorkerData;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.services.WorkerInfrastructureService;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.MappingSettings;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.ViewParameters;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.WorkerDataItem;
import cz.cuni.mff.d3s.jdeeco.cloudsimulator.administration.web.client.data.WorkerDataItemImpl;

@Controller
public class InfrastructureController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(InfrastructureController.class);

	private static final String INFRASTRUCUTRE_VIEW = "main/infrastructure/infrastructure";;


	@Resource
	private WorkerInfrastructureService workerInfrastructureService;
	
	
	@RequestMapping(value = MappingSettings.INFRASTRUCTURE)
	public ModelAndView showInfrastructure(HttpServletRequest request) {

		ClientModelAndView modelAndView = getDefaultModelAnView(INFRASTRUCUTRE_VIEW);
		
		try {
			List<WorkerData> workerDatas = workerInfrastructureService.getWorkerDatas();
			List<WorkerDataItem> workerDataItems = workerDatas.stream().map(x -> new WorkerDataItemImpl(x)).collect(Collectors.toList());
			
			modelAndView.addObject("workers", workerDataItems);
		}
		catch (Exception e) {
			modelAndView.addObject(ViewParameters.ERROR_MSG_VAR, e);
		}
		
		return modelAndView;
	}

	private ClientModelAndView getDefaultModelAnView(String viewName) {
		return new ClientModelAndView(viewName);
	}
}
