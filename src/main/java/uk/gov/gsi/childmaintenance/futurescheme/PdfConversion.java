package uk.gov.gsi.childmaintenance.futurescheme;

/*
 * Copyright 2018 Department for Works and Pension
 *
 * Licensed under the AGNU AFFERO GENERAL PUBLIC LICENSE version 3.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/AGPL-3.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import java.io.IOException;
import java.io.InputStream;

import uk.gov.gsi.childmaintenance.futurescheme.logger.LALogging;
import uk.gov.gsi.childmaintenance.futurescheme.models.FilePaths;

import java.nio.file.WatchService;
import java.util.Properties;
import java.util.concurrent.Executors;

import java.nio.file.ClosedWatchServiceException;

public class PdfConversion {

	
	public static final FilePaths filePaths = new FilePaths();
	public static void main(String[] argv) throws IOException {

		String resourceName = "applicationpaths.properties"; // could also be a constant
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		final Properties props = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			props.load(resourceStream);
		}
		Utils.setProperties(props);
		
		if (argv.length >= 1 && argv[0] != null && Utils.checkNumber(argv[0])) {
			PdfConversion.filePaths.setExecutor(Executors.newFixedThreadPool(Integer.valueOf(argv[0])));
		} else {
			PdfConversion.filePaths.setExecutor(Executors.newFixedThreadPool(10));
		}

		// Register file watcher service on program start
		final WatchService watchService = Utils.registerWatchService();
		// Process left over META files from Ready folder
		Utils.processStockFiles();
		LALogging.info("Adding shutdown hook ");
		// Add shutdown hook before processing File Watcher events
		Utils.addShutDownHook(watchService);

		// Process file watcher events
		if (watchService != null) {
			try {
				Utils.runWatchService(watchService);
			} catch (InterruptedException e) {
				LALogging.error("Watcher Service Interupted ");
				Thread.currentThread().interrupt();
			} catch (ClosedWatchServiceException e) {
				LALogging.error("Watcher Service is closed by Shutdown Hook ");
			}
		} else {
			LALogging.error("Watch service is null. Utility exiting.");
		}

	}
}
