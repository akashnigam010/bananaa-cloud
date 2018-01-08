package in.socyal.sc.helper.aws;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class S3Helper {
	private ResourceBundle resource = ResourceBundle.getBundle("aws-creds");
	private static final String ACCESS_KEY = "access.key.id";
	private static final String SECRET_KEY = "secret.access.key.id";
	private static final String BUCKET_NAME = "bna.s3.bucket.name";

	public String saveUserImage(String userImageUrl, String nameId) throws IOException {
		AmazonS3Client s3Client = new AmazonS3Client(credential());
		URL url = new URL(userImageUrl);
		BufferedImage img = ImageIO.read(url);
		File file = new File(nameId);
		ImageIO.write(img, "jpg", file);

		s3Client.setEndpoint("https://s3.amazonaws.com");
		s3Client.putObject(new PutObjectRequest(resource.getString(BUCKET_NAME), nameId + ".jpg", file)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		//return s3Client.getResourceUrl(resource.getString(BUCKET_NAME), nameId + ".jpg");
		// Only returning the nameId + .jpg since the logic for complete url is moved now
		return nameId + ".jpg";
	}

	public AWSCredentials credential() {
		return new BasicAWSCredentials(resource.getString(ACCESS_KEY), resource.getString(SECRET_KEY));
	}
}
