package com.hy.tools.exception;

import java.io.Writer;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkException implements TemplateExceptionHandler {

	@Override
	public void handleTemplateException(
			TemplateException paramTemplateException,
			Environment paramEnvironment, Writer paramWriter)
			throws TemplateException {
	}

}
