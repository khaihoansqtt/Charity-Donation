package com.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.DonationDTO;
import com.dto.UserDTO;
import com.entity.Role;
import com.service.DonationService;
import com.service.RoleService;
import com.service.UserDonationService;
import com.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private HttpSession session;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private DonationService donationService;
	@Autowired
	private UserDonationService userDonationService;
	
	@GetMapping()
	public String adminHome() {
		return "admin/home";	// Trả về trang admin home
	}
	
	@GetMapping("/manage-users")
	public String manageUser(Model model) {
		// Kiểm tra xem có phải admin đang dùng không
		if (isAdmin()) {
			List<UserDTO> usersDTO = userService.getAllUsers(); // Lấy tất cả user để hiển thị
			List<Role> roles = roleService.getAllRoles(); //Lấy các role để truyền qua giao diện ở form tạo mới/cập nhật (select-option)
			UserDTO newUserDTO = new UserDTO();	  // Tạo newUserDTO để truyền qua giao diện ở form tạo new user
			
			model.addAttribute("users", usersDTO);
			model.addAttribute("newUser", newUserDTO);
			model.addAttribute("roles", roles);
			return "admin/account";		// Trả về trang quản lý user
		} else {
			return "redirect:/user";	// Nếu k phải là admin thì chuyển đến trang user
		}
	}
	
	// Tạo mới một user
	@PostMapping("/add-new-user")
	public String addNewUser(@ModelAttribute("newUser") UserDTO userDTO) {
		if (isAdmin()) {
			userService.addNewUser(userDTO);
			System.out.println("Add a new user successfully");
			return "redirect:/admin/manage-users";
		} else {
			return "redirect:/user";
		}
	}
	
	// Khóa/mở khóa một user
	@PostMapping("/lock-user")
	public String lockUser(@RequestParam("userId") int userId) {
		if (isAdmin()) {
			userService.lockUser(userId);
			System.out.println("Lock/unlock user successfully");
			return "redirect:/admin/manage-users";
		} else {
			return "redirect:/user";
		}
	}
	
	// Cập nhật một user
	@PostMapping("/update-user")
	public String updateUser(
			@RequestParam("userId") int userId,
			@RequestParam("fullName") String fullName,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("address") String address,
			@RequestParam("roleId") int roleId
			) {
		if (isAdmin()) {
			userService.updateUser(userId, fullName, phoneNumber, address, roleId);
			System.out.println("Update user successfully");
			
			return "redirect:/admin/manage-users";
		} else {
			return "redirect:/user";
		}
	}
	
	// Xóa một user bằng userId
	@PostMapping("/delete-user")
	public String deleteUser(@RequestParam("userId") int userId) {
		if (isAdmin()) {
			userService.deleteUser(userId);
			System.out.println("Delete user successfully");
			
			return "redirect:/admin/manage-users";
		} else {
			return "redirect:/user";
		}
	}
	
	// Trả về trang quản lý các đợt donations
	@GetMapping("/manage-donations")
	public String manageDonation(Model model) {
		if (isAdmin()) {
			List<DonationDTO> donationsDTO = donationService.getAllDonations();  // Lấy list donation để hiển thị ở view
			DonationDTO newDonationDTO = new DonationDTO();		// Để tạo mới donation
			
			model.addAttribute("donations", donationsDTO);
			model.addAttribute("newDonation", newDonationDTO);	// Để tạo mới donation
			
			return "admin/donation";
		} else {
			return "redirect:/user";
		}
	}
	
	// Tạo mới một đợt donation
	@PostMapping("/add-new-donation")
	public String addNewDonation(@ModelAttribute("newDonation") DonationDTO donationDTO) {
		if (isAdmin()) {
			donationService.addNewDonation(donationDTO);
			System.out.println("Add a new donation successfully");
			
			return "redirect:/admin/manage-donations";
		} else {
			return "redirect:/user";
		}
	}
	
	// Xóa một đợt donation bằng id
	@PostMapping("/delete-donation")
	public String deleteDonation(@RequestParam("donationId") int donationId) {
		if (isAdmin()) {
			donationService.deleteDonation(donationId);
			System.out.println("Delete donation successfully");
			
			return "redirect:/admin/manage-donations";
		} else {
			return "redirect:/user";
		}
	}
	
	// Xem thông tin chi tiết một đợt donation bằng donationId
	@GetMapping("/detail/{donationId}")
	public String detailDonation(@PathVariable("donationId") int donationId, Model model) {
		if (isAdmin()) {
			DonationDTO donationDTO = donationService.getDonation(donationId);
			model.addAttribute("donation", donationDTO);
			return "admin/detail";
		} else {
			return "redirect:/user";
		}
	}
	
	// Thay đổi trạng thái một đợt donation (mới tạo -> đang quyên góp -> kết thúc -> đóng quyên góp)
	@PostMapping("/change-donation-status") 
	public String changeDonationStatus(@RequestParam("donationId") int donationId) {
		if (isAdmin()) {
			donationService.changeStatus(donationId);
			System.out.println("Change donation status successfully");
			return "redirect:/admin/manage-donations";
		} else {
			return "redirect:/user";
		}
	}
	
	// Cập nhật thông tin một đợt donation
	@PostMapping("/update-donation")
	public String updateDonation(
			@RequestParam("donationId") int donationId,
			@RequestParam("code") String code,
			@RequestParam("name") String name,
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
			@RequestParam("organizationName") String organizationName,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("description") String description
			) {
		if (isAdmin()) {
			donationService.updateDonation(donationId, code, name, startDate, endDate, organizationName, phoneNumber, description);
			System.out.println("Update donation successfully");
			return "redirect:/admin/manage-donations";
		} else {
			return "redirect:/user";
		}
	}
	
	// Xác nhận người dùng đã chuyển tiền chưa (thay đổi status của user_donation)
	@GetMapping("/confirm-userDonation")
	public String confirmUserDonation(@RequestParam("userDonationId") int userDonationId) {
		if (isAdmin()) {
			int donationId = userDonationService.confirmUserDonation(userDonationId);
			return "redirect:/admin/detail/" + donationId;
		} else {
			return "redirect:/user";
		}
	}
	
	// Kiểm tra phiên có phải của admin không
	public boolean isAdmin() {
		UserDTO loginedUser = (UserDTO) session.getAttribute("loginedUser");
		if (loginedUser != null && loginedUser.getRoleId() == 1) return true;
		else return false;
	}
}
