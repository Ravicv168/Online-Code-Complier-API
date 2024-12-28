package com.example.compilerapi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.compilerapi.model.CodeRequest;

@RestController
@RequestMapping("/api/complie")
public class CodeExecutorController {

	@PostMapping
	public String executeCode(@RequestBody CodeRequest coderequest) {
		return "Code executed";
	}
}
