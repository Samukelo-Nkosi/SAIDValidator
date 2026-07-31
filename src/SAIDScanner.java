import java.util.Scanner;

public class SAIDScanner
{

    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a 13-digit SA ID number: ");
        String idNumber = input.nextLine().trim();

        scanID(idNumber);

        input.close();
    }

    public static void scanID(String id)
    {
        if (!isValidLength(id)) {
            System.out.println("Error: ID number must be exactly 13 digits long.");
            return;
        }

        if (!isNumeric(id)) {
            System.out.println("Error: ID number must contain only digits.");
            return;
        }

        if (!isValidChecksum(id)) {
            System.out.println("Warning: Checksum validation failed. This ID number may not be valid.");
            // Not returning here so you can still see the breakdown if desired
        }

        String gender = extractGender(id);
        String dob = extractDateOfBirth(id);
        String citizenship = extractCitizenship(id);

        System.out.println("----- ID Validation -----");
        System.out.println("ID Number   : " + id);
        System.out.println("Date of Birth (YYMMDD): " + dob);
        System.out.println("Gender      : " + gender);
        System.out.println("Citizenship : " + citizenship);
        System.out.println("---------------------------");
    }

    // Check the ID is exactly 13 characters
    public static boolean isValidLength(String id) {
        return id != null && id.length() == 13;
    }

    // Check the ID contains only digits
    public static boolean isNumeric(String id) {
        return id.matches("\\d{13}");
    }

    // Extract gender based on digits 7-10 (index 6 to 10)
    public static String extractGender(String id) {
        try {
            String genderDigits = id.substring(6, 10);
            int genderValue = Integer.parseInt(genderDigits);
            return (genderValue < 5000) ? "Female" : "Male";
        }

        catch (NumberFormatException e) {
            return "Unknown (invalid gender digits)";
        }
    }

    // Extract and format date of birth from YYMMDD
    public static String extractDateOfBirth(String id) {
        String yy = id.substring(0, 2);
        String mm = id.substring(2, 4);
        String dd = id.substring(4, 6);

        int month = Integer.parseInt(mm);
        int day = Integer.parseInt(dd);

        if (month < 1 || month > 12 || day < 1 || day > 31) {
            return "Invalid date (" + yy + "-" + mm + "-" + dd + ")";
        }

        return "20" + yy + "-" + mm + "-" + dd + ")"; // assumes 2000s; adjust logic for 1900s if needed
    }

    // Extract citizenship status from digit 11 (index 10)
    public static String extractCitizenship(String id) {
        char citizenDigit = id.charAt(10);
        if (citizenDigit == '0') {
            return "SA Citizen";
        } else if (citizenDigit == '1') {
            return "Permanent Resident";
        } else {
            return "Unknown";
        }
    }

    // Luhn checksum validation for the 13th digit
    // NOTE: return type is boolean, and this exact name (isValidChecksum,
    // lowercase 's') is what must be used everywhere it is called.
    public static boolean isValidChecksum(String id) {
        try {
            int sum = 0;
            boolean alternate = false;

            // Process digits from right to left, excluding the check digit itself
            for (int i = id.length() - 2; i >= 0; i--) {
                int digit = Character.getNumericValue(id.charAt(i));

                if (alternate) {
                    digit *= 2;
                    if (digit > 9) {
                        digit -= 9;
                    }
                }

                sum += digit;
                alternate = !alternate;
            }

            int checkDigit = Character.getNumericValue(id.charAt(id.length() - 1));
            int calculatedCheckDigit = (10 - (sum % 10)) % 10;

            return checkDigit == calculatedCheckDigit;
        } catch (Exception e) {
            return false;
        }
    }
}

