package org.jumahuaca.extensions;

import org.easybatch.core.filter.RecordFilter;
import org.easybatch.core.job.Job;
import org.easybatch.core.job.JobBuilder;
import org.easybatch.core.job.JobExecutor;
import org.easybatch.core.job.JobReport;
import org.easybatch.core.processor.RecordProcessor;
import org.easybatch.core.reader.RecordReader;
import org.easybatch.core.record.Record;
import org.easybatch.core.writer.RecordWriter;

import org.junit.jupiter.api.extension.Extension;

@SuppressWarnings("rawtypes")
public class EasyBatchExtension<P extends Record, R extends Record> implements Extension {

	private static final String JOB_NAME = "TESTING EXTENSION JOB";

	public JobReport launchTestingBatch(RecordReader reader, RecordFilter<P> filter, RecordProcessor<P, R> processor,
			RecordWriter writer, TestDoubleBatchHelper helper) {
		helper.mockInjectionsReadOk();
		helper.mockInjectionsProcessOk();
		helper.mockInjectionsWriteOk();
		
		Job job = new JobBuilder().named(JOB_NAME).reader(reader).filter(filter).processor(processor).writer(writer)
				.build();

		JobExecutor jobExecutor = new JobExecutor();
		JobReport report = jobExecutor.execute(job);
		jobExecutor.shutdown();
		return report;
	}

}
