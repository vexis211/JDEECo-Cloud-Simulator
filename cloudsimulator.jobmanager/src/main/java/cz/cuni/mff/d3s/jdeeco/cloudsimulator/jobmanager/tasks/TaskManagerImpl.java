package cz.cuni.mff.d3s.jdeeco.cloudsimulator.jobmanager.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class TaskManagerImpl implements TaskManager {

    @Autowired
    private ApplicationContext applicationContext;

//    @Override
//    public MultipleDownloadTask getMultipleDownloadTask(int downloadArchiveId) {
//		return (MultipleDownloadTask) applicationContext.getBean(
//			"multipleDownloadTask", new Object[] { downloadArchiveId });
//    }
}
