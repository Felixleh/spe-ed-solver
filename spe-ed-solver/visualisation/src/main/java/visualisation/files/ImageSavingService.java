package visualisation.files;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import visualisation.NamedImage;

public class ImageSavingService {

	private static final String DEFAULT_FILE_EXTENSION = "png";

	public void saveImage(final File parent, final NamedImage image) throws ImageSavingException {
		saveImage(parent, image, "");
	}

	public void saveImage(final File parent, final NamedImage image, String appendix) throws ImageSavingException {
		parent.mkdirs();
		final File targetFile = new File(parent.toString() + "/" + image.getName() + appendix + "." + DEFAULT_FILE_EXTENSION);
		try {
			ImageIO.write(image.getImage(), DEFAULT_FILE_EXTENSION, targetFile);
		} catch (final IOException e) {
			throw new ImageSavingException("File could not be saved!", e);
		}
	}

}
