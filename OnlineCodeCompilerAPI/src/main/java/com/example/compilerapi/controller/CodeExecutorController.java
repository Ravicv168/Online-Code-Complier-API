package com.example.compilerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.compilerapi.model.CodeRequest;
import com.example.compilerapi.service.CodeExecutorService;

@RestController
@RequestMapping("/api/compile")
public class CodeExecutorController {
	
	@Autowired
	CodeExecutorService codeExecutorService;

	@PostMapping
	public String executeCode(@RequestBody CodeRequest coderequest) {
		return codeExecutorService.executeCode(coderequest);
	}
}
