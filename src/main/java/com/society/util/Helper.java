package com.society.util;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

enum complaintStatus{
	PENDING, RESOLVED, UNRESOLVED;
}
enum Roles{
	GUARD,RESIDENT;
}
enum targetRole{
	ALL, GUARD, RESIDENT;
}
public class Helper {

	public static int choiceInput(int limit) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println(str.enterChoice);

            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if(value<=limit && value>0) {
					return value;
				} else {
					System.out.println(str.invalidInput);
				}

            } catch (NumberFormatException e) {
                System.out.println(str.invalidInput);
            }
        }
	}


	public static boolean ComplaintStatus(String status)
	{
		if(status==null)
		{
			return false;
		}
		else
		{
			String statusUpperCase= status.toUpperCase().trim();
			for(complaintStatus Cstatus: complaintStatus.values())
			{
				if(Cstatus.name().equals(statusUpperCase)) {
					return true;
				}
			}
			return false;
		}
	}

	public static String generateUniqueId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().substring(24);
	}

	public static String hashPassword(String password) {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	public static boolean isEmailValid(String email) {

		if (email == null || email.trim().isEmpty()) {
			System.out.println("Email cannot be null or empty.");
			return false;
		}


		int atIndex = email.indexOf('@');
		if (atIndex == -1) {
			System.out.println("Email must contain '@' symbol.");
			return false;
		}


		String localPart = email.substring(0, atIndex);
		String domainPart = email.substring(atIndex + 1);


		if (localPart.isEmpty() || domainPart.isEmpty()) {
			System.out.println("Email must contain both local and domain parts(eg- @gmail.com).");
			return false;
		}


		int dotIndex = domainPart.indexOf('.');
		if (dotIndex == -1 || dotIndex == domainPart.length() - 1) {
			System.out.println("Domain part must contain a period and a valid top-level domain.");
			return false;
		}


		for (char ch : email.toCharArray()) {
			if (!Character.isLetterOrDigit(ch) && ch != '@' && ch != '.' && ch != '_' && ch != '-') {
				System.out.println("Email contains invalid characters.");
				return false;
			}
		}

		return true;
	}
	public static boolean isPasswordValid(String password) {

		if (password.length() < 8) {
			System.out.println("Password must be at least 5 characters long.");
			return false;
		}
		if (!password.matches(".*[A-Z].*")) {
			System.out.println("Password must contain at least one uppercase letter.");
			return false;
		}
		if (!password.matches(".*[a-z].*")) {
			System.out.println("Password must contain at least one lowercase letter.");
			return false;
		}
		if (!password.matches(".*\\d.*")) {
			System.out.println("Password must contain at least one digit.");
			return false;
		}
		if (!password.matches(".*[@#$%^&+=].*")) {
			System.out.println("Password must contain at least one special character (@#$%^&+=).");
			return false;
		}
		return true;
	}

	public static boolean isPhoneNumberValid(String phoneNumber) {

		return phoneNumber.matches("\\d{10}");
	}

	public static boolean isUsernameValid(String username) {

		if (username == null || username.trim().isEmpty()) {
			System.out.println("Username cannot be null or empty.");
			return false;
		}


		if (username.length() < 3 || username.length() > 15) {
			System.out.println("Username must be between 3 and 15 characters long.");
			return false;
		}


		for (char ch : username.toCharArray()) {
			if (!Character.isLetterOrDigit(ch) && ch != '_' && ch != '-') {
				System.out.println("Username can only contain letters, digits, underscores, and hyphens.");
				return false;
			}
		}
		if (!Character.isLetter(username.charAt(0))) {
			System.out.println("Username must start with a letter.");
			return false;
		}
		return true;
	}

	public static boolean isUserRoleValid(String userRole) {
		if(userRole==null) {
			return false;
		} else
		{
			String userRoleUpperCase= userRole.toUpperCase().trim();
			for(Roles role: Roles.values())
			{
				if(role.name().equals(userRoleUpperCase)) {
					return true;
				}
			}
			return false;
		}
	}

	public static boolean isValidDate(String date) {

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {

			LocalDate.parse(date, dateFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public  static boolean isValidTarget(String target) {
		if(target==null) {
			return false;
		} else
		{
			String targetUppercase=target.toUpperCase();
			for(targetRole role:targetRole.values())
			{
				if(role.name().equals(targetUppercase)) {
					return true;
				}
			}
			return false;
		}
	}
	public static boolean isValidTime(String time) {
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		try {
			LocalTime.parse(time, timeFormatter);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
	public static boolean notNullCheck(String string) {
	    if (string == null || string.trim().isEmpty()) {
	        return false;
	    }
	    return true;
	}
	public static void printFunction(String string) {
		System.out.println(string);
	}

}



