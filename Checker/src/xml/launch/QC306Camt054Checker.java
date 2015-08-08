package xml.launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class QC306Camt054Checker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SchemaFactory factory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		InputSource sourceentree;
		try {
			sourceentree = new InputSource(new FileInputStream(new File(
					"C:/Users/jourdang/Google Drive/Workspace/Checker/resources/camt054/xsd/camt.054.001.02.xsd.xml")));
			SAXSource sourceXSD = new SAXSource(sourceentree);
			Schema schema = factory.newSchema(sourceXSD);
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File("C:/Users/jourdang/Google Drive/Workspace/Checker/resources/camt054/files/QC_306.xml")));
			validator.validate(new StreamSource(new File("C:\\Users\\jourdang\\Google Drive\\Workspace\\Generator\\generate\\NewFile3.xml")));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
	}

}
