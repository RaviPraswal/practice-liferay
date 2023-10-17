package com.liferay.test.employees.mvc.command.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.SecureRandom;
import com.liferay.test.employees.constants.MVCCommandNames;
import com.liferay.test.employees.constants.PracticeLiferayEmployeesWebPortletKeys;

import java.io.File;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;


import org.osgi.service.component.annotations.Component;

@Component(property = { "javax.portlet.name=" + PracticeLiferayEmployeesWebPortletKeys.PRACTICELIFERAYEMPLOYEESWEB,
		"mvc.command.name=" + MVCCommandNames.AUDIO_CAPTCHA_ACTION_COMMAND }, service = MVCActionCommand.class)

public class AudioCaptchaActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest request, ActionResponse response) throws Exception {
		try {
			log.info("-------> inside AudioCaptchaActionCommand");
			String audioCaptchaText = getAudioCaptchaText();
			log.info("-------> audioCaptchaText : "+audioCaptchaText);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public static String getAudioCaptchaText() {
		SecureRandom random = new SecureRandom();
		int x = random.nextInt(9) + 1; // (int) Math.floor((Math.random()*9)+1);

		int y = random.nextInt(9) + 1; // (int)Math.floor((Math.random() * 9) + 1);
		int a = random.nextInt(9) + 1; // (int)Math.floor((Math.random() * 9) + 1);

		int b = random.nextInt(9) + 1; // (int)Math.floor((Math.random() * 9) + 1);

		String z = x + " " + y + " " + a + " " + b;
		return z;
	}

	private static final Log log = LogFactoryUtil.getLog(AudioCaptchaActionCommand.class.getName());
}
