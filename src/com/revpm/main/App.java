package com.revpm.main;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revpm.model.PasswordEntry;
import com.revpm.model.SecurityQuestion;
import com.revpm.model.User;
import com.revpm.service.OTPService;
import com.revpm.service.PasswordService;
import com.revpm.service.SecurityQuestionService;
import com.revpm.service.UserService;

public class App {
	private static Scanner scn = new Scanner(System.in);
	private static final String[] DEFAULT_QUESTIONS = {
			"What is your favorite color?", "What is your birth city?",
			"What is your nickname ?", "What is your favorite food?",
			"What is your childhood friend name?" };

	private static final Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		try{
		logger.info("APPLICATION STARTED");

		while (true) {
			System.out.println("\n=== REV PASSWORD MANAGER===");
			System.out.println("1.Register");
			System.out.println("2.Login");
			System.out.println("3.Forgot Password");
			System.out.println("4.Exit");
			System.out.println("Choose:");

			int choice = scn.nextInt();
			scn.nextLine();
			switch (choice) {
			case 1:
				register();
				break;
			case 2:
				login();
				break;
			case 3:
				forgotPassword();
				break;
			case 4:
				System.out.println("Thank you visit again!!!");
				System.exit(0);
			default:
				System.out.println("Invalid choice");
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	// email validation
	private static boolean isValidEmail(String email) {
		return email.matches("^[A-Za-z0-9.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
	}

	// 01.for user register
	private static void register() {
		try{
		System.out.print("Enter name: ");
		String name = scn.nextLine();

		String email;
		while (true) {
			System.out.print("Enter email: ");
			email = scn.nextLine();
			if (isValidEmail(email))break;
			else
				System.out.println("Invalid email format! Please enter a valid email.");
		}

		System.out.print("Enter master password: ");
		String password = scn.nextLine();

		System.out.print("Re-enter master password: ");
		String rePassword = scn.nextLine();

		if (!password.equals(rePassword)) {
			System.out.println("Passwords do not match!");
			return;
		}

		User user = UserService.register(name, email, password);

		if (user == null)
			System.out.println("Email already exists!");
		else
			System.out.println("Registration successful!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 02.for user login
	private static void login() {
		try{
		String email;
		while (true) {
			System.out.print("Enter email: ");
			email = scn.nextLine();
			if (isValidEmail(email))
				break;
			else
				System.out.println("Invalid email format! Please enter a valid email.");
		}
		System.out.print("Enter master password: ");
		String password = scn.nextLine();

		User user = UserService.login(email, password);

		if (user == null)
			System.out.println("User details not found, First register then login");
		else {
			System.out.println("Login successful. Welcome " + user.getName());
			dashboard(user);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 03.for forgetting user password recovery
	private static void forgotPassword() {
		try{
		String email;
		while (true) {
			System.out.print("Enter your registered email: ");
			email = scn.nextLine();
			if (isValidEmail(email))
				break;
			else
				System.out.println("Invalid email format! Please enter a valid email.");
		}

		User user = UserService.getUserByEmail(email);

		if (user == null) {
			System.out.println("User this email not found!");
			return;
		}
		List<SecurityQuestion> questions = SecurityQuestionService
				.getQuestionsByUser(user.getUserId());

		if (questions.isEmpty()) {
			System.out.println("No security questions set!");
			return;
		}
		int correctCount = 0;
		for (int i = 0; i < questions.size(); i++) {
			SecurityQuestion q = questions.get(i);
			System.out.println("Q: " + q.getQuestion());
			System.out.print("Your answer: ");
			String userAnswer = scn.nextLine();

			if (q.getAnswer().equalsIgnoreCase(userAnswer)) {
				correctCount++;
			}
		}

		if (correctCount == questions.size()) {
			System.out.println("Verification successful!");

			System.out.print("Enter new master password: ");
			String newPass = scn.nextLine();

			System.out.print("Re-enter new master password: ");
			String rePass = scn.nextLine();

			if (!newPass.equals(rePass)) {
				System.out.println("Passwords do not match!");
				return;
			}
			UserService.updateMasterPassword(user.getUserId(), newPass);

			System.out.println("Master password reset successfully!");
		} else {
			System.out.println("Incorrect answers. Access denied.");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 01.for user details..
	private static void viewProfile(User user) {
		System.out.println("\n--- Profile ---");
		System.out.println("Name: " + user.getName());
		System.out.println("Email: " + user.getEmail());
	}

	// 02.for profile update
	private static void updateProfile(User user) {

		try{
		System.out.print("Enter new name: ");
		String newName = scn.nextLine();

		String newEmail;
		while (true) {
			System.out.print("Enter new email: ");
			newEmail = scn.nextLine();
			if (isValidEmail(newEmail))
				break;
			else
				System.out.println("Invalid email format!");

		}
		UserService.updateProfile(user.getUserId(), newName, newEmail);
		user.setName(newName);
		user.setEmail(newEmail);

		System.out.println("Profile updated successfully!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 03.for generating strong auto password for account passwords

	private static void generatePassword() {
		try{
		System.out.print("Enter Account password length: ");
		int length = scn.nextInt();
		scn.nextLine();

		System.out.print("Include uppercase? (true/false): ");
		boolean upper = scn.nextBoolean();

		System.out.print("Include lowercase? (true/false): ");
		boolean lower = scn.nextBoolean();

		System.out.print("Include numbers? (true/false): ");
		boolean numbers = scn.nextBoolean();

		System.out.print("Include symbols? (true/false): ");
		boolean symbols = scn.nextBoolean();

		scn.nextLine();

		String password = com.revpm.util.PasswordGenerator.generate(length,
				upper, lower, numbers, symbols);

		if (password.isEmpty()) {
			System.out.println("Select at least one character type!");
		} else {
			System.out.println("Generated Password: " + password);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 04.for adding new account Password in user
	private static void addNewPassword(User user) {
		try{
		System.out.print("Enter account name: ");
		String account = scn.nextLine();

		System.out.print("Enter username: ");
		String username = scn.nextLine();

		System.out.print("Enter password: ");
		String password = scn.nextLine();

		PasswordService.addPassword(account, username, password,user.getUserId());

		System.out.println("Account Password Details saved successfully!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 05.for list of all Passwords
	private static void listAllPasswords(User user) {
        try{
		List<PasswordEntry> list = PasswordService.getPasswordsByUser(user.getUserId());

		if (list.isEmpty()) {
			System.out.println("No Account passwords found!");
			return;
		}

		System.out.println("\nYour Saved Accounts:");
		for (PasswordEntry p : list) {
			System.out.println("ID: " + p.getId() + " | Account: "+ p.getAccountName());
		}
        }catch(Exception e){
			e.printStackTrace();
		}
	}

	// 06.for showing all Passwords in present user
	private static void viewPasswordDetails(User user) {

		try{
		System.out.print("Enter Account Password Entry ID: ");
		int id = scn.nextInt();
		scn.nextLine();

		System.out.print("Re-enter your master password: ");
		String master = scn.nextLine();

		if (!user.getMasterPassword().equals(master)) {
			System.out.println("Invalid master password! Access denied.");
			return;
		}

		String otp = OTPService.generateOTP(user.getUserId());
		System.out.println("Your OTP is: " + otp);

		System.out.print("Enter OTP: ");
		String enteredOtp = scn.nextLine();

		if (!OTPService.validateOTP(user.getUserId(), enteredOtp)) {
			System.out.println("Invalid or expired OTP!");
			return;
		}

		PasswordEntry entry = PasswordService.getPasswordById(id,
				user.getUserId());

		if (entry == null) {
			System.out.println("Account Password entry not found!");
			return;
		}

		System.out.println("\n--- Password Details ---");
		System.out.println("Account: " + entry.getAccountName());
		System.out.println("Username: " + entry.getUsername());
		System.out.println("Password: " + entry.getPassword());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 07.for updating account Passwords in user
	private static void updatePassword(User user) {

		try{
		System.out.print("Enter Account Password Entry ID: ");
		int id = scn.nextInt();
		scn.nextLine();

		System.out.print("Re-enter your master password: ");
		String master = scn.nextLine();

		if (!user.getMasterPassword().equals(master)) {
			System.out.println("Invalid master password! Access denied.");
			return;
		}

		String otp = OTPService.generateOTP(user.getUserId());
		System.out.println("Your OTP is: " + otp);

		System.out.print("Enter OTP: ");
		String enteredOtp = scn.nextLine();

		if (!OTPService.validateOTP(user.getUserId(), enteredOtp)) {
			System.out.println("Invalid or expired OTP!");
			return;
		}

		System.out.print("Enter new password: ");
		String newPassword = scn.nextLine();

		PasswordService.updatePassword(id, newPassword);

		System.out.println("Account password updated successfully!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 08.for deleting a account passwords in user
	private static void deletePassword(User user) {
 
		try{
		System.out.print("Enter Password Entry ID to delete: ");
		int id = scn.nextInt();
		scn.nextLine();

		System.out.print("Re-enter your master password: ");
		String master = scn.nextLine();

		if (!user.getMasterPassword().equals(master)) {
			System.out.println("Invalid master password! Access denied.");
			return;
		}

		String otp = OTPService.generateOTP(user.getUserId());
		System.out.println("Your OTP is: " + otp);

		System.out.print("Enter OTP: ");
		String enteredOtp = scn.nextLine();

		if (!OTPService.validateOTP(user.getUserId(), enteredOtp)) {
			System.out.println("Invalid or expired OTP!");
			return;
		}

		PasswordService.deletePassword(id, user.getUserId());

		System.out.println("Account password deleted successfully!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 09. for searching account Passwords through account name
	private static void searchPassword(User user) {

		try{
		System.out.print("Enter account name to search: ");
		String keyword = scn.nextLine();

		List<PasswordEntry> results = PasswordService.search(keyword,
				user.getUserId());

		if (results.isEmpty()) {
			System.out.println("No matching accounts found!");
			return;
		}

		System.out.println("\nMatching Accounts:");
		for (PasswordEntry p : results) {
			System.out.println("ID: " + p.getId() + " | Account: "
					+ p.getAccountName());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 10.for adding security questions
	private static void addSecurityQuestion(User user) {

		try{
		// fetch already added questions
		List<SecurityQuestion> existing = SecurityQuestionService
				.getQuestionsByUser(user.getUserId());

		if (existing.size() >= 2) {
			System.out.println("You can add only 2 security questions.");
			return;
		}

		System.out.println("\nSelect a security question:");

		for (int i = 0; i < DEFAULT_QUESTIONS.length; i++) {
			System.out.println((i + 1) + ". " + DEFAULT_QUESTIONS[i]);
		}

		System.out.print("Enter option number: ");
		int choice = scn.nextInt();
		scn.nextLine();

		if (choice < 1 || choice > DEFAULT_QUESTIONS.length) {
			System.out.println("Invalid selection!");
			return;
		}

		String selectedQuestion = DEFAULT_QUESTIONS[choice - 1];

		// prevent duplicate question
		for (SecurityQuestion q : existing) {
			if (q.getQuestion().equalsIgnoreCase(selectedQuestion)) {
				System.out.println("This question is already selected.");
				return;
			}
		}

		System.out.print("Enter your answer: ");
		String answer = scn.nextLine();

		SecurityQuestionService.addQuestion(selectedQuestion, answer,user.getUserId());

		System.out.println("Security question added successfully!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 11.for viewing security questions
	private static void viewSecurityQuestions(User user) {
		try{
		List<SecurityQuestion> list = SecurityQuestionService
				.getQuestionsByUser(user.getUserId());

		if (list.isEmpty()) {
			System.out.println("No security questions found!");
			return;
		}

		System.out.println("\nYour Security Questions:");
		for (int i = 0; i < list.size(); i++) {
			SecurityQuestion q = list.get(i);
			System.out.println("ID: " + q.getId() + " | Question: "+ q.getQuestion());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 12.for updating security Questions in dash board
	private static void updateSecurityQuestion(User user) {
		try{
		System.out.print("Enter Security Question ID: ");
		int id = scn.nextInt();
		scn.nextLine();

		System.out.print("Enter new answer: ");
		String newAnswer = scn.nextLine();

		boolean updated = SecurityQuestionService.updateAnswer(id,user.getUserId(), newAnswer);

		if (updated) {
			System.out.println("Security question updated successfully!");
		} else {
			System.out.println("Security question not found!");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 13. for updating security Questions in dash board
	private static void deleteSecurityQuestion(User user) {
		try{
		System.out.print("Enter Security Question ID to delete: ");
		int id = scn.nextInt();
		scn.nextLine();

		boolean deleted = SecurityQuestionService.deleteQuestion(id,
				user.getUserId());

		if (deleted) {
			System.out.println("Security question deleted successfully!");
		} else {
			System.out.println("Security question not found!");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// 14.for updating master password
	private static void changeMasterPassword(User user) {

		try{
		System.out.print("Enter current master password: ");
		String current = scn.nextLine();

		if (!user.getMasterPassword().equals(current)) {
			System.out.println("Current master password is incorrect!");
			return;
		}

		System.out.print("Enter new master password: ");
		String newPass = scn.nextLine();

		System.out.print("Re-enter new master password: ");
		String rePass = scn.nextLine();

		if (!newPass.equals(rePass)) {
			System.out.println("Passwords do not match!");
			return;
		}

		UserService.updateMasterPassword(user.getUserId(), newPass);

		// Update in-memory user object also
		user.setMasterPassword(newPass);

		System.out.println("Master password updated successfully!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// for user dash board

	private static void dashboard(User user) {
		try{
		while (true) {
			System.out.println("\n--- Dashboard ---");
			System.out.println("1. View Profile");
			System.out.println("2. Update Profile");
			System.out.println("3. Generate Strong Account Password");
			System.out.println("4. Add New Account Password");
			System.out.println("5. List All Account Passwords");
			System.out.println("6. View Account Password Details");
			System.out.println("7. Update Account Password");
			System.out.println("8. Delete Account Password");
			System.out.println("9. Search Account Password");
			System.out.println("10. Add Security Question");
			System.out.println("11. View Security Questions");
			System.out.println("12. Update Security Question");
			System.out.println("13. Delete Security Question");
			System.out.println("14. Change User Master Password");
			System.out.println("15. Logout");

			System.out.print("Choose: ");

			int ch = scn.nextInt();
			scn.nextLine();

			switch (ch) {
			case 1:
				viewProfile(user);
				break;
			case 2:
				updateProfile(user);
				break;
			case 3:
				generatePassword();
				break;
			case 4:
				addNewPassword(user);
				break;
			case 5:
				listAllPasswords(user);
				break;
			case 6:
				viewPasswordDetails(user);
				break;
			case 7:
				updatePassword(user);
				break;
			case 8:
				deletePassword(user);
				break;
			case 9:
				searchPassword(user);
				break;
			case 10:
				addSecurityQuestion(user);
				break;
			case 11:
				viewSecurityQuestions(user);
				break;
			case 12:
				updateSecurityQuestion(user);
				break;
			case 13:
				deleteSecurityQuestion(user);
				break;
			case 14:
				changeMasterPassword(user);
				break;
			case 15:
				return;
			default:
				System.out.println("Invalid!");
			}
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
