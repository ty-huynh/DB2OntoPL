package dh.protege41.db2onto.common;

import java.util.HashMap;
import java.util.Map;

public class MessagesUtility {
	
	Map<String, String> warnings = new HashMap<String, String>();
	Map<String, String> successes = new HashMap<String, String>();
	Map<String, String> errors = new HashMap<String, String>();
	Map<String, String> infos = new HashMap<String, String>();
	
	public MessagesUtility() {}
	
	public void addWarning(String tag, String message) {
		addMessage(tag, message, warnings);
	}
	
	public void addSuccess(String tag, String message) {
		addMessage(tag, message, successes);
	}
	
	public void addError(String tag, String message) {
		addMessage(tag, message, errors);
	}
	
	public void addInfo(String tag, String message) {
		addMessage(tag, message, infos);
	}
	
	public boolean hasErrors() {
		return (errors.size() > 0) ? true : false;
	}
	
	private void addMessage(String tag, String message, Map<String, String> map) {
		map.put(tag, message);
	}

	
	public Map<String, String> getWarnings() {
		return warnings;
	}

	public Map<String, String> getSuccesses() {
		return successes;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public Map<String, String> getInfos() {
		return infos;
	}

	public String warnings() {
		return toHtmlString(warnings);
	}

	public String successes() {
		return toHtmlString(successes);
	}

	public String errors() {
		return toHtmlString(errors);
	}
	
	public String infos() {
		return toHtmlString(infos);
	}

	public String toHtmlString(Map<String, String> map) {
		StringBuilder messages = new StringBuilder();
		for(String key : map.keySet()) {
			messages.append("\n");
			messages.append(key).append(" : ");
			messages.append(map.get(key));
		}
		return messages.toString();
	}
	
	public void clearAll() {
		this.errors.clear();
		this.successes.clear();
		this.warnings.clear();
	}
	
	public void clear(Map<String, String> map) {
		map.clear();
	}
	
	public static String addHtmlTag(String message) {
		StringBuilder html = new StringBuilder();
		html.append("<html>").append(message).append("</html>");
		return html.toString();
	}
}
