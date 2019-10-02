package org.jumahuaca.extensions;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.Extension;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class SpringBatchChunkExtension<R, P> implements Extension {

	public List<P> simulateSimpleRun(ItemReader<R> reader, ItemProcessor<R, P> processor, ItemWriter<P> writer,
			TestDoubleBatchHelper helper)
			throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
		helper.mockInjectionsReadOk();
		helper.mockInjectionsProcessOk();
		helper.mockInjectionsWriteOk();
		List<P> written = new ArrayList<P>();
		boolean keepIterating = true;
		while (keepIterating) {
			R actual = reader.read();
			if (actual == null) {
				keepIterating = false;
			} else {
				P processed = processor.process(actual);
				if(processed!=null) {
					List<P> processedList = new ArrayList<P>();
					processedList.add(processed);
					written.add(processed);
					writer.write(processedList);					
				}
			}
		}
		return written;
	}

}
