package com.example.hcm.feihuread.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

public class CharsetDetector {
	
	/**
	 * 检测当前文件的编码方式
	 */
	public static Charset detect(InputStream in) {
		CodepageDetectorProxy detector=CodepageDetectorProxy.getInstance();
		detector.add(JChardetFacade.getInstance());
		detector.add(UnicodeDetector.getInstance());
		detector.add(ASCIIDetector.getInstance());

		Charset charset = null;
		try {
			in.mark(100);
			charset = detector.detectCodepage(in, 100);
			in.reset();
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
		return charset;
	}
	
}
