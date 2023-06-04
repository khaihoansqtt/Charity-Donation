package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dto.DonationDTO;
import com.dto.UserDTO;
import com.service.DonationService;
import com.service.UserDonationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	HttpSession session;
	@Autowired
	private DonationService donationService;
	@Autowired
	private UserDonationService userDonationService;
	
	// Hiển thị các đợt donation ở màn hình home của user, mặc định trang đầu tiên là trang 0
	@GetMapping()
	private String home(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, 
							Model model
						) {
		if (isLogin()) {
			// Lấy đối tượng Page của donation, ở đây là 5 donation / 1 trang
			Page<DonationDTO> donationsPage = donationService.getPage(pageNumber, 5);
			model.addAttribute("donationsPage", donationsPage);		// Truyền Page object qua view
			return "public/home";
		} else {
			return "redirect:/";	// Nếu chưa đăng nhập thì hiển thị trang đăng nhập
		}
	}
	
	
	// Xem chi tiết một đợt donation
	@GetMapping("/detail-donation/{donationId}")
	public String detailDonation(@PathVariable("donationId") int donationId, Model model) {
		if (isLogin()) {
			
		} else {
			return "redirect:/";
		}
		DonationDTO donationDTO = donationService.getDonation(donationId);
		model.addAttribute("donation", donationDTO);
		return "public/detail";
	}
	
	@PostMapping("/donate")
	public String donate(@RequestParam("donationId") int donationId,
						@RequestParam("name") String name,
						@RequestParam("money") int money,
						@RequestParam("text") String text,
						
						// Trường này để xác định redirect về trang /user hay user/detail-donation
						@RequestParam("redirectTo") int redirectTo,
						RedirectAttributes redirectAttributes) {

		if (isLogin()) {
			int userId = getSessionUserDTO().getId();
			userDonationService.addUserDonation(donationId, userId, name, money, text);
			
			redirectAttributes.addFlashAttribute("msg", "");
			if (redirectTo == 0) {
				return "redirect:/user";	// Nếu redirectTo == 0 thì redirect tới trang user home
			} else return "redirect:/user/detail-donation/" + donationId; // Nếu redirectTo != 0 thì redirect tới trang detail donation
		} else {
			return "redirect:/";
		}
	}
	
	// Lấy đối tượng UserDTO của user đang đăng nhập
	public UserDTO getSessionUserDTO() {
		return (UserDTO) session.getAttribute("loginedUser");
	}
	
	// Kiểm tra người dùng đã đăng nhập chưa
	public boolean isLogin() {
		UserDTO loginedUser = getSessionUserDTO();
		if (loginedUser == null) return false;
		else return true;
	}
}
