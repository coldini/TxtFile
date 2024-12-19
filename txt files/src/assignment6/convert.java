  package assignment6;
import java.util.Scanner;
import java.io.*;

public class convert{
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner reader = new Scanner(System.in);
		
		System.out.print("Please enter the name of the txt file." );
		String txt_file = reader.next();
		File f = new File(txt_file);
		Scanner input = new Scanner(f);
		
		String htmlname = get_name(txt_file);
		File htmlFile = new File(htmlname);
		PrintStream output = new PrintStream(htmlFile);
		
		String line = "";
		String previous = "";
		String out = "";
		
		output.println("<html>");
		output.println("<body>");
		boolean isListOpen = false;
		while(input.hasNextLine()) {
			line = input.nextLine();
			out = conv(line, previous);
			if(!line.isEmpty() && line.charAt(0) == '-') {
				if(!isListOpen) {
					isListOpen = true;
				}
			} else {
				if(isListOpen) {
					output.println("</ul>");
				isListOpen = false;
				}
			}
			output.println(out);
			previous = line;
			
		}
		output.println("</body>");
		output.println("</html>");
		output.close();
		reader.close();
		input.close();
		}

	
	private static String get_name(String fileName) {
		int index = fileName.indexOf('.');
		int str_len = fileName.length();
		String hold = "";
		
		for(int i = 0; i < str_len; i++) {
			if (i<index) {
				hold+= fileName.charAt(i);
			}
		}
		hold+= ".html";
		return hold;
		
	}
	
	private static String conv(String line1, String line2) {
		String printed = " ";
		if(   !line1.isEmpty() && (line2.isEmpty() || line2.charAt(0) != '-' )&& line1.charAt(0) == '-') {
			printed = "<ul>";
			printed+= "\n" + listing(line1);
			
			
		}else if(!line1.isEmpty() && !line2.isEmpty() && ((line2.charAt(0) == '-' || line2.isEmpty()) && line1.charAt(0) == '-')){
			  printed = listing(line1);
			
		}else if(line1.isEmpty()) {
			printed = "<p>";
			
		}else if(!line1.isEmpty() && line1.charAt(0) == '#') {
			printed = head(line1);
			
		}else if(line1.contains("[[") && line1.contains("]]")) {
			printed = urlIt(line1);
		}else {
			printed = "<p>" + line1 + "</ br>";
		}
		return printed;
}
	
	
	
	public static String urlIt(String linkie) {
		int start = linkie.indexOf("[[");
		int end = linkie.indexOf("]]");
		
		String links = linkie;
		
		if(start != -1 && end != -1 && end > start) {
			String temp = linkie.substring(start +2, end);
			String[] parts = temp.split("\\]\\[");
			
			if(parts.length == 2) {
				String url = parts[0];
				String text = parts[1];
				
				links = "<a href=\"" + url + "\">" +text + "</a>" + linkie.substring(end+2) ; 			}
		}
		return links;
	}
	private static String head(String line) {
		int stringlen = line.length();
		
		char first = line.charAt(0);
		char last = line.charAt(stringlen - 1);
		String temp = "";
		if((first == '#') && (last == '#')) {
			temp += "<h1>";
			
			for(int i = 1; i<stringlen-1; i++){
				temp += line.charAt(i);
			}
			temp+= "</h1>";
		}
		return temp;
	}
	
	private static String listing(String line) {
		String hold = "";
		String out = "";
		for(int i = 1; i < line.length(); i++) {
			hold += line.charAt(i);
		}
		out = "<li>" + hold + "</li>";
		return out;
	}
	
	
	
	
	
}