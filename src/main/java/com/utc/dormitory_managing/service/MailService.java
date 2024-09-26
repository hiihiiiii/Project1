package com.utc.dormitory_managing.service;

import java.nio.charset.StandardCharsets;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.utc.dormitory_managing.dto.MailDTO;
import com.utc.dormitory_managing.dto.StaffDTO;
import com.utc.dormitory_managing.dto.StudentDTO;
import com.utc.dormitory_managing.entity.Staff;
import com.utc.dormitory_managing.entity.Student;
import com.utc.dormitory_managing.repository.StaffRepo;
import com.utc.dormitory_managing.repository.StudentRepo;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.NoResultException;


public interface MailService {
	void sendEmail(StudentDTO studentDTO, String subject, StaffDTO staffDTO);

}

@Service
class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	private StudentRepo studentRepo;	

	@Autowired
	private StaffRepo staffRepo;
	
	@Async
	@Override
	public void sendEmail(StudentDTO studentDTO, String subject, StaffDTO staffDTO) {
		MailDTO mailDTO = new MailDTO();

		try {
			Student student = studentRepo.findById(studentDTO.getStudentId()).orElseThrow(NoResultException::new);
			Staff staff = staffRepo.findById(staffDTO.getStaffId()).orElseThrow(NoResultException::new);
			
			String senderName = staff.getFullname();
			String senderEmail = staff.getStaffEmail();

			
			String receiverName = student.getFullname();
			String receiverEmail = student.getStudentEmail();

			mailDTO.setSubject(subject);

			MimeMessage email = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(email, StandardCharsets.UTF_8.name());

			mailDTO.setContent("test");

			
			// Load template email with content
			Context context = new Context();
			context.setVariable("senderName", senderName);
			context.setVariable("senderEmail", senderEmail);
			context.setVariable("receiverName", receiverName);

			String html = templateEngine.process("email", context);

			// Send email
			helper.setTo(receiverEmail);
			helper.setText(html, true);
			helper.setSubject(mailDTO.getSubject());
			helper.setFrom(senderEmail);

			javaMailSender.send(email);
			

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
