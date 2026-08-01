# SAIDScanner

A simple Java command-line tool that reads a 13-digit South African ID number, validates its structure, and extracts the information encoded within it — date of birth, gender, and citizenship status.

> **Note:** This tool performs *local format validation only*. It checks whether an ID number is well-formed according to the SA ID numbering scheme; it does **not** verify against the Department of Home Affairs (DHA) population register, and cannot confirm that an ID number actually belongs to a real person.

## Features

- Validates that input is exactly 13 digits
- Validates the checksum digit using the Luhn algorithm
- Extracts and formats date of birth (YYMMDD)
- Determines gender from the ID's sequence number
- Determines citizenship status (SA citizen / permanent resident)
- Prints a clean, formatted summary table to the console

## How it works

A South African ID number follows the format `YYMMDDSSSSCAZ`:

| Segment | Position | Meaning |
|---|---|---|
| `YYMMDD` | 1–6 | Date of birth |
| `SSSS` | 7–10 | Gender sequence number |
| `C` | 11 | Citizenship (0 = SA citizen, 1 = permanent resident) |
| `A` | 12 | Legacy sequence digit |
| `Z` | 13 | Checksum digit (Luhn algorithm) |

Gender is derived from the sequence number:
- `0000`–`4999` → Female
- `5000`–`9999` → Male

## Getting started

### Prerequisites

- Java Development Kit (JDK) 8 or later

### Running the program

```bash
javac SAIDScanner.java
java SAIDScanner
```

You'll be prompted to enter a 13-digit ID number:

```
Enter a 13-digit SA ID number: 9202205089081
```

### Example output

```
_____________________________________________________________
|***** ID Validation *****|************** INFO *************|
|_________________________|_________________________________|
|ID Number:               |9202205089081|
|Date of Birth (YYMMDD):  |2092-02-20|
|Gender:                  |Male|
|Citizenship:             |SA Citizen|
|_________________________|_________________________________|
|_________________________|_________________________________|
```

## Project structure

```
SAIDScanner.java   # single-file program: input, validation, extraction, output
```

## Known limitations

- **Century ambiguity:** the ID number only stores a 2-digit year, so the program currently assumes the 2000s. It cannot distinguish, for example, 1978 from 2078 without additional logic.
- **Checksum failures are non-blocking:** a failed Luhn check only prints a warning rather than stopping execution, so a fabricated ID number can still be scanned and summarised.
- **No real identity verification:** this tool cannot confirm an ID number belongs to an actual registered person. Genuine verification requires an accredited integration with the Department of Home Affairs.

## Roadmap / possible extensions

- [ ] Calculate age from date of birth
- [ ] Resolve century ambiguity (1900s vs 2000s)
- [ ] Support scanning multiple ID numbers in one session
- [ ] Wrap logic in a REST API (e.g. Spring Boot) for use by a GUI or other services
- [ ] Add a proper GUI front end
- [ ] Export scan results to CSV/JSON

## Compliance note

If this project is extended to store or process real ID numbers, note that South Africa's **Protection of Personal Information Act (POPIA)** applies. ID numbers are personal information, and any system handling them at scale should implement appropriate consent, encryption, retention limits, and access logging.

## License

Add your preferred license here (e.g. MIT).
