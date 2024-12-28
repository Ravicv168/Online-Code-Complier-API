package com.example.compilerapi.service;

import java.io.*;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.example.compilerapi.model.CodeRequest;
import com.example.compilerapi.model.CodeResponse;

@Service
public class CodeExecutorService {

	public CodeResponse executeCode(CodeRequest coderequest) {
		String output = "";
	    String error = "";
	    boolean success = false;
	    String language = coderequest.getLanguage();
		try {
			switch (coderequest.getLanguage().toLowerCase()) {
            case "java":
                output = executeJavaCode(coderequest.getCode());
                success = true;
                break;
//            case "c":
//                output = executeCCode(coderequest.getCode());
//                break;
//            case "cpp":
//                output = executeCppCode(coderequest.getCode());
//                success = true;
//                break;
            case "python":
                output = executePythonCode(coderequest.getCode());
                success = true;
                break;
            default:
                output = "Unsupported language: " + coderequest.getLanguage();
                break;
			}
		}catch (Exception e) {
			error="Error Executing Code: "+ e.getMessage();
		}
		return new CodeResponse(output, error, success, language, "Execution Complete");
	}
	
	private String executeJavaCode(String code) throws Exception{
		File file = saveCodeToFile(code, ".java");
		String classname = file.getName().replace(".java", "");
		
		if(!compileJavaCode(file)) {
			return "Compilation failed";
		}
		
		return runJavaCode(classname);
	}
	
	private String executePythonCode(String code) throws Exception {
        File sourceFile = saveCodeToFile(code, ".py");

        return runPythonCode(sourceFile);
    }
	
	private File saveCodeToFile(String code, String fileextension) throws IOException {
		String classname = extractClassName(code);
		File tempfile=new File(classname + fileextension);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempfile))) {
            writer.write(code);
        }
        return tempfile;
	}
	
	private String extractClassName(String code) {
	    int classIndex = code.indexOf("class ");
	    if (classIndex != -1) {
	        int endIndex = code.indexOf(" ", classIndex + 6);   
	        return code.substring(classIndex + 6, endIndex != -1 ? endIndex : code.indexOf("\n", classIndex));
	    }
	    return "UnknownClass";  
	}
	
	private boolean compileJavaCode(File sourceFile) throws Exception {
        Process compileProcess = new ProcessBuilder("javac", sourceFile.getAbsolutePath()).start();
        
//        StringBuilder output = new StringBuilder();
//        StringBuilder errorOutput = new StringBuilder();
//
//        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(compileProcess.getInputStream()));
//             BufferedReader stdError = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()))) {
//
//            String line;
//            while ((line = stdInput.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//
//            while ((line = stdError.readLine()) != null) {
//                errorOutput.append(line).append("\n");
//            }
//        }

       
        int exitCode = compileProcess.waitFor();

//        if (exitCode != 0) {
//            System.out.println("Compilation Failed!");
//            System.out.println("Error Output:\n" + errorOutput.toString());
//            return false;
//        } else {
//            System.out.println("Compilation Success!");
//            System.out.println("Output:\n" + output.toString());
//        }

        return exitCode == 0;
    }
	
	private String runJavaCode(String className) throws Exception {
        Process runProcess = new ProcessBuilder("java", className).start();
        return readProcessOutput(runProcess);
    }
	
	private String runPythonCode(File sourceFile) throws Exception {
        Process process = new ProcessBuilder("python", sourceFile.getAbsolutePath()).start();
        return readProcessOutput(process);
    }
	
	private String readProcessOutput(Process process) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }
        return output.toString();
    }
}
