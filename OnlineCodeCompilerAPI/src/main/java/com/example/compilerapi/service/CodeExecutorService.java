package com.example.compilerapi.service;

import java.io.*;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.compilerapi.model.CodeRequest;
import com.example.compilerapi.model.CodeResponse;

@Service
public class CodeExecutorService {
	
	private static final Logger logger = LoggerFactory.getLogger(CodeExecutorService.class);

	// Method to execute the code based on the language
	@Async("taskExecutor")
	public CompletableFuture<CodeResponse> executeCode(CodeRequest coderequest) {
		logger.info("Executing code on thread: {}", Thread.currentThread().getName());
		String output = "";
	    String error = "";
	    boolean success = false;
	    String language = coderequest.getLanguage();
	    String message = "Execution Complete";
		try {
			switch (coderequest.getLanguage().toLowerCase()) {
            case "java":
                output = executeJavaCode(coderequest.getCode());
                success = true;
                break;
            case "python":
                output = executePythonCode(coderequest.getCode());
                success = true;
                break;
            default:
                output = "Unsupported language: " + coderequest.getLanguage();
                message = "Execution failed";
                break;
			}
		}catch (Exception e) {
			error="Error Executing Code: "+ e.getMessage();
			message = "Execution failed";
		}
	
		return CompletableFuture.completedFuture(new CodeResponse(output, error, success, language, message));
	}
	
	// Method to execute Java code
	private String executeJavaCode(String code) throws Exception{
		
		logger.info("Executing Java code on thread: {}", Thread.currentThread().getName());
		// Save Java code to a temporary file
		File file = saveCodeToFile(code, ".java");
		String classname = file.getName().replace(".java", "");
		
		// Compile Java code
		if(!compileJavaCode(file)) {
			return "Compilation failed";
		}
		// Run Java code
		return runJavaCode(classname);
	}
	
	// Method to execute Java code
	private String executePythonCode(String code) throws Exception {
		
		logger.info("Executing python code on thread: {}", Thread.currentThread().getName());
		// Save Python code to a temporary file
        File sourceFile = saveCodeToFile(code, ".py");

        // Execute Python code
        return runPythonCode(sourceFile);
    }
	
	// Helper method to save code to a temporary file
	private File saveCodeToFile(String code, String fileextension) throws IOException {
		String classname = extractClassName(code);
		File tempfile=new File(classname + fileextension);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempfile))) {
            writer.write(code);
        }
        return tempfile;
	}
	
	// Helper method to extract the class name.
	private String extractClassName(String code) {
	    int classIndex = code.indexOf("class ");
	    if (classIndex != -1) {
	        int endIndex = code.indexOf(" ", classIndex + 6);   
	        return code.substring(classIndex + 6, endIndex != -1 ? endIndex : code.indexOf("\n", classIndex));
	    }
	    return "Temp";  
	}
	
	// Helper method to compile Java code
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
	
	// Helper method to run Java code
	private String runJavaCode(String className) throws Exception {
        Process runProcess = new ProcessBuilder("java", className).start();
        return readProcessOutput(runProcess);
    }
	
	// Helper method to run Python code
	private String runPythonCode(File sourceFile) throws Exception {
        Process process = new ProcessBuilder("python", sourceFile.getAbsolutePath()).start();
        return readProcessOutput(process);
    }
	
	 // Helper method to read output from the process
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
