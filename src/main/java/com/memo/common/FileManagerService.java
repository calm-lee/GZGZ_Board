package com.memo.common;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component //﻿범용적인 Spring bean
public class FileManagerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// 실제 이미지가 저장될 컴퓨터 경로
	public final static String FILE_UPLOAD_PATH = "D:\\이의연\\Spring Project\\ex\\memo_workspace\\Memo\\images"; //상수값(변경불가)은 대문자로 표시한다.

	// 이미지를 저장 -> 이미지의 URL path 리턴
	public String saveFile(String userLoginId, Multipartfile file) {
		
		// 파일을 컴퓨터에 저장
		// 1. 파일 디렉토리 경로 만듦(겹치지 않게)  예: marobiana_20210819173033/sun.png (아이디_업로드날짜시각/파일명.확장자)
		String directoryName = userLoginId + "_" + System.currentTimeMillis() + "/";
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(filePath);
		if (directory.mkdir() == false) { // mkdir(): 파일을 업로드할 filePath 경로에 폴더 생성을 한다.
			// 디렉토리 생성 실패
			logger.error("[파일업로드] 디렉토리 생성 실패" + userLoginId + "," + filePath);
			return null;
		}; 
		
		// 이미지 URL을 만들어 리턴
	}
}