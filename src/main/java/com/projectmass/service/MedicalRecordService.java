package com.example.medical.service;

import com.example.medical.model.MedicalRecord;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;

public class MedicalRecordService {
    // Directory where the text files will be stored
    private static final String DIRECTORY = "records/";

    public MedicalRecordService() {
        File dir = new File(DIRECTORY);
        if (!dir.exists()) {
            dir.mkdirs(); // Create the directory if it doesn't exist
        }
    }

    // 1. Create Record
    public void createRecord(MedicalRecord record) throws IOException {
        File file = new File(DIRECTORY + record.getPatientId() + ".txt");
        if (file.exists()) {
            throw new IOException("Record for Patient ID " + record.getPatientId() + " already exists.");
        }
        record.setCreatedAt(LocalDateTime.now());
        saveToFile(record, file);
    }

    // 2. Read Record
    public MedicalRecord readRecord(String patientId) throws IOException {
        File file = new File(DIRECTORY + patientId + ".txt");
        if (!file.exists()) {
            throw new FileNotFoundException("Record for Patient ID " + patientId + " not found.");
        }

        Properties props = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            props.load(reader);
        }

        MedicalRecord record = new MedicalRecord();
        record.setPatientId(props.getProperty("patientId"));
        record.setFullName(props.getProperty("fullName"));

        String dob = props.getProperty("dob");
        if (dob != null && !dob.isEmpty()) record.setDob(LocalDate.parse(dob));

        String age = props.getProperty("age");
        if (age != null && !age.isEmpty()) record.setAge(Integer.parseInt(age));

        record.setGender(props.getProperty("gender"));
        record.setBloodGroup(props.getProperty("bloodGroup"));
        record.setAddress(props.getProperty("address"));
        record.setPhoneNumber(props.getProperty("phoneNumber"));
        record.setEmailAddress(props.getProperty("emailAddress"));
        record.setEmergencyContactName(props.getProperty("emergencyContactName"));
        record.setEmergencyContactNumber(props.getProperty("emergencyContactNumber"));
        record.setRelationshipToPatient(props.getProperty("relationshipToPatient"));
        record.setMedicalHistory(props.getProperty("medicalHistory"));
        record.setSurgicalHistory(props.getProperty("surgicalHistory"));
        record.setFamilyMedicalHistory(props.getProperty("familyMedicalHistory"));
        record.setAllergies(props.getProperty("allergies"));
        record.setCurrentMedications(props.getProperty("currentMedications"));
        record.setVaccinationHistory(props.getProperty("vaccinationHistory"));
        record.setCurrentSymptoms(props.getProperty("currentSymptoms"));
        record.setDiagnosis(props.getProperty("diagnosis"));
        record.setDoctorNotes(props.getProperty("doctorNotes"));
        record.setBloodPressure(props.getProperty("bloodPressure"));

        String heartRate = props.getProperty("heartRate");
        if (heartRate != null && !heartRate.isEmpty()) record.setHeartRate(Integer.parseInt(heartRate));

        String temp = props.getProperty("temperature");
        if (temp != null && !temp.isEmpty()) record.setTemperature(Double.parseDouble(temp));

        String weight = props.getProperty("weight");
        if (weight != null && !weight.isEmpty()) record.setWeight(Double.parseDouble(weight));

        String height = props.getProperty("height");
        if (height != null && !height.isEmpty()) record.setHeight(Double.parseDouble(height));

        record.setLabTestResults(props.getProperty("labTestResults"));
        record.setImagingReports(props.getProperty("imagingReports"));
        record.setPrescriptions(props.getProperty("prescriptions"));
        record.setTreatmentPlan(props.getProperty("treatmentPlan"));

        String admDate = props.getProperty("admissionDate");
        if (admDate != null && !admDate.isEmpty()) record.setAdmissionDate(LocalDateTime.parse(admDate));

        String disDate = props.getProperty("dischargeDate");
        if (disDate != null && !disDate.isEmpty()) record.setDischargeDate(LocalDateTime.parse(disDate));

        record.setFollowUpDetails(props.getProperty("followUpDetails"));
        record.setAttendingDoctorName(props.getProperty("attendingDoctorName"));
        record.setDepartmentWard(props.getProperty("departmentWard"));
        record.setInsuranceDetails(props.getProperty("insuranceDetails"));
        record.setPaymentInformation(props.getProperty("paymentInformation"));
        record.setConsentForms(props.getProperty("consentForms"));
        record.setDoctorSignature(props.getProperty("doctorSignature"));

        String createdAt = props.getProperty("createdAt");
        if (createdAt != null && !createdAt.isEmpty()) record.setCreatedAt(LocalDateTime.parse(createdAt));

        String lastUpdated = props.getProperty("lastUpdated");
        if (lastUpdated != null && !lastUpdated.isEmpty()) record.setLastUpdated(LocalDateTime.parse(lastUpdated));

        return record;
    }

    // 3. Update Record
    public void updateRecord(MedicalRecord record) throws IOException {
        File file = new File(DIRECTORY + record.getPatientId() + ".txt");
        if (!file.exists()) {
            throw new FileNotFoundException("Record for Patient ID " + record.getPatientId() + " not found.");
        }
        record.setLastUpdated(LocalDateTime.now());
        saveToFile(record, file);
    }

    // 4. Delete Record
    public boolean deleteRecord(String patientId) {
        File file = new File(DIRECTORY + patientId + ".txt");
        return file.exists() && file.delete();
    }

    // Helper method to write the object properties to the text file using BufferedWriter
    private void saveToFile(MedicalRecord record, File file) throws IOException {
        Properties props = new Properties();

        putIfNotNull(props, "patientId", record.getPatientId());
        putIfNotNull(props, "fullName", record.getFullName());
        putIfNotNull(props, "dob", record.getDob());
        putIfNotNull(props, "age", record.getAge());
        putIfNotNull(props, "gender", record.getGender());
        putIfNotNull(props, "bloodGroup", record.getBloodGroup());
        putIfNotNull(props, "address", record.getAddress());
        putIfNotNull(props, "phoneNumber", record.getPhoneNumber());
        putIfNotNull(props, "emailAddress", record.getEmailAddress());
        putIfNotNull(props, "emergencyContactName", record.getEmergencyContactName());
        putIfNotNull(props, "emergencyContactNumber", record.getEmergencyContactNumber());
        putIfNotNull(props, "relationshipToPatient", record.getRelationshipToPatient());
        putIfNotNull(props, "medicalHistory", record.getMedicalHistory());
        putIfNotNull(props, "surgicalHistory", record.getSurgicalHistory());
        putIfNotNull(props, "familyMedicalHistory", record.getFamilyMedicalHistory());
        putIfNotNull(props, "allergies", record.getAllergies());
        putIfNotNull(props, "currentMedications", record.getCurrentMedications());
        putIfNotNull(props, "vaccinationHistory", record.getVaccinationHistory());
        putIfNotNull(props, "currentSymptoms", record.getCurrentSymptoms());
        putIfNotNull(props, "diagnosis", record.getDiagnosis());
        putIfNotNull(props, "doctorNotes", record.getDoctorNotes());
        putIfNotNull(props, "bloodPressure", record.getBloodPressure());
        putIfNotNull(props, "heartRate", record.getHeartRate());
        putIfNotNull(props, "temperature", record.getTemperature());
        putIfNotNull(props, "weight", record.getWeight());
        putIfNotNull(props, "height", record.getHeight());
        putIfNotNull(props, "labTestResults", record.getLabTestResults());
        putIfNotNull(props, "imagingReports", record.getImagingReports());
        putIfNotNull(props, "prescriptions", record.getPrescriptions());
        putIfNotNull(props, "treatmentPlan", record.getTreatmentPlan());
        putIfNotNull(props, "admissionDate", record.getAdmissionDate());
        putIfNotNull(props, "dischargeDate", record.getDischargeDate());
        putIfNotNull(props, "followUpDetails", record.getFollowUpDetails());
        putIfNotNull(props, "attendingDoctorName", record.getAttendingDoctorName());
        putIfNotNull(props, "departmentWard", record.getDepartmentWard());
        putIfNotNull(props, "insuranceDetails", record.getInsuranceDetails());
        putIfNotNull(props, "paymentInformation", record.getPaymentInformation());
        putIfNotNull(props, "consentForms", record.getConsentForms());
        putIfNotNull(props, "doctorSignature", record.getDoctorSignature());
        putIfNotNull(props, "createdAt", record.getCreatedAt());
        putIfNotNull(props, "lastUpdated", record.getLastUpdated());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            props.store(writer, "Patient Medical Record");
        }
    }

    private void putIfNotNull(Properties props, String key, Object value) {
        if (value != null) {
            props.setProperty(key, value.toString());
        }
    }
}

