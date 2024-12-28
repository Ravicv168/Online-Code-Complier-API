package com.example.compilerapi.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.compilerapi.model.CodeRequest;
import com.example.compilerapi.model.CodeResponse;
import com.example.compilerapi.service.CodeExecutorService;

@RestController
@RequestMapping("/api/compile")
public class CodeExecutorController {
	
	@Autowired
	CodeExecutorService codeExecutorService;

	@PostMapping("execute")
	public CompletableFuture<CodeResponse> executeCode(@RequestBody CodeRequest coderequest) {
		return codeExecutorService.executeCode(coderequest);
	}
}
