package com.profiling.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportBuilder {

	private static final ReportBuilder htmlFacto = new ReportBuilder();
	private static final String htmlFileName = "the-file-name.html";
	private static final String cssFileName = "treeview.css";
	private static String pathDir;
	private static FileOutputStream writer;
		

	private ReportBuilder() {
	}

	public static void generateReport(String outputDir) throws IOException {
		
		// TODO add test last char eq. to / if exist.
//		pathDir = outputDir;
//		generateHtml();
//		generateCss();
	}

	public static void generateHtml() throws IOException {
		writer = new FileOutputStream(new File(pathDir + htmlFileName));
		writer.write("<!DOCTYPE html>".getBytes());
		writer.write("<html>".getBytes());
		htmlFacto.generateHtmlHeader();
		htmlFacto.generateHtmlBody();
		writer.write("</html>".getBytes());
		writer.close();
	}

	private void generateHtmlHeader() throws IOException {
		writer.write("<head>".getBytes());
		writer.write("<title>Exemple - treeview</title>".getBytes());
		writer.write("".getBytes());
		writer.write("<style type=\"text/css\">".getBytes());
		writer.write("	body{".getBytes());
		writer.write("		margin:10px 30px;".getBytes());
		writer.write("		font-family:verdana;".getBytes());
		writer.write("		font-size:12px;".getBytes());
		writer.write("	}".getBytes());
		writer.write("	h1{".getBytes());
		writer.write("		font-family:verdana;".getBytes());
		writer.write("		font-size:14px;".getBytes());
		writer.write("		font-weight:bold;".getBytes());
		writer.write("		text-decoration:underline;".getBytes());
		writer.write("	}".getBytes());
		writer.write("</style>".getBytes());
		writer.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"treeview.css\" media=\"screen\" /> ".getBytes());
		writer.write("<script type=\"text/javascript\" src=\"jquery.js\"></script>".getBytes());
		writer.write("<script type=\"text/javascript\">".getBytes());
		writer.write("$(function() {".getBytes());
		writer.write("	$(\'div.tree div:has(div)\').addClass(\'parent\');".getBytes());
		writer.write("	$(\'div.tree div\').click(function() {".getBytes());
		writer.write("		var o = $(this);".getBytes());
		writer.write("		o.children(\'div\').toggle();".getBytes());
		writer.write("		o.filter(\'.parent\').toggleClass(\'expanded\');".getBytes());
		writer.write("		return false;".getBytes());
		writer.write("	});".getBytes());
		writer.write("});".getBytes());
		writer.write("</script>".getBytes());
		writer.write("/head>".getBytes());
	}
	
	private void generateHtmlBody() throws IOException {
		writer.write("<body>".getBytes());
		writer.write("".getBytes());
		writer.write("<h1>Exemple de mise en place d'un treeview</h1>".getBytes());
		writer.write("".getBytes());
		writer.write("<p>".getBytes());
		writer.write("	<br />".getBytes());
		writer.write("	<div class='tree' style='padding:10px; border:solid 1px #dedede;'>".getBytes());
		writer.write("		<div>Catégorie 1".getBytes());
		writer.write("			<div>Sous catégorie 1".getBytes());
		writer.write("				<div>Texte élément 1</div>".getBytes());
		writer.write("				<div>Texte élément 2</div>".getBytes());
		writer.write("				<div>Texte élément 3</div>".getBytes());
		writer.write("			</div>".getBytes());
		writer.write("			<div>Sous catégorie 2</div>".getBytes());
		writer.write("			<div>Sous catégorie 3</div>".getBytes());
		writer.write("		</div>".getBytes());
		writer.write("		<div>Catégorie 2".getBytes());
		writer.write("			<div>Sous catégorie 1</div>".getBytes());
		writer.write("			<div>Sous catégorie 2".getBytes());
		writer.write("				<div>Texte élément 1</div>".getBytes());
		writer.write("				<div>Texte élément 2</div>".getBytes());
		writer.write("				<div>Texte élément 3</div>".getBytes());
		writer.write("			</div>".getBytes());
		writer.write("			<div>Sous catégorie 3</div>".getBytes());
		writer.write("		</div>".getBytes());
		writer.write("		<div>Catégorie 3".getBytes());
		writer.write("			<div>Sous catégorie 1</div>".getBytes());
		writer.write("			<div>Sous catégorie2</div>".getBytes());
		writer.write("			<div>Sous catégorie 3".getBytes());
		writer.write("				<div>Texte élément 1</div>".getBytes());
		writer.write("				<div>Texte élément 2</div>".getBytes());
		writer.write("				<div>Texte élément 3</div>".getBytes());
		writer.write("			</div>".getBytes());
		writer.write("		</div>".getBytes());
		writer.write("	</div>".getBytes());
		writer.write("	<br />".getBytes());
		writer.write("	<br />".getBytes());
		writer.write("	<br />".getBytes());
		writer.write("</p>".getBytes());
		writer.write("".getBytes());
		writer.write("</body>".getBytes());
	}

	private static void generateCss() throws IOException {
		writer = new FileOutputStream(new File(pathDir + cssFileName));
		writer.write("div.tree div {".getBytes());
		writer.write("	 padding-left:16px;".getBytes());
		writer.write("	}".getBytes());
		writer.write("	div.tree div.parent div {".getBytes());
		writer.write("	 display:none;".getBytes());
		writer.write("	 cursor:default;".getBytes());
		writer.write("	}".getBytes());
		writer.write("	div.tree div.parent {".getBytes());
		writer.write("	 cursor:pointer !important;".getBytes());
		writer.write("	 background:transparent url(plus.gif) no-repeat top left;".getBytes());
		writer.write("	}".getBytes());
		writer.write("	div.tree div.expanded {".getBytes());
		writer.write("	 background:transparent url(moins.gif) no-repeat top left;".getBytes());
		writer.write("	}".getBytes());
		writer.close();
	}

}
