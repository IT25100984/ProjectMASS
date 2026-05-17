package com.example.medical.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MedicalRecord {

    // --- Identity ---
    private String patientId;
    private String fullName;
    private LocalDate dob;
    private Integer age;
    private String gender;
    private String bloodGroup;

    // --- Contact ---
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private String relationshipToPatient;

    // --- History ---
    private String medicalHistory;
    private String surgicalHistory;
    private String familyMedicalHistory;
    private String allergies;
    private String currentMedications;
    private String vaccinationHistory;

    // --- Clinical ---
    private String currentSymptoms;
    private String diagnosis;
    private String doctorNotes;

    // --- Vitals ---
    private String bloodPressure;
    private Integer heartRate;
    private Double temperature;
    private Double weight;
    private Double height;

    // --- Reports ---
    private String labTestResults;
    private String imagingReports;
    private String prescriptions;
    private String treatmentPlan;

    // --- Admin ---
    private LocalDateTime admissionDate;
    private LocalDateTime dischargeDate;
    private String followUpDetails;
    private String attendingDoctorName;
    private String departmentWard;
    private String insuranceDetails;
    private String paymentInformation;
    private String consentForms;

    // --- Audit ---
    private String doctorSignature;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    // Default Constructor
    public MedicalRecord() {
    }

    // All-args Constructor
    public MedicalRecord(String patientId, String fullName, LocalDate dob, Integer age, String gender, String bloodGroup,
                         String address, String phoneNumber, String emailAddress, String emergencyContactName,
                         String emergencyContactNumber, String relationshipToPatient, String medicalHistory,
                         String surgicalHistory, String familyMedicalHistory, String allergies,
                         String currentMedications, String vaccinationHistory, String currentSymptoms,
                         String diagnosis, String doctorNotes, String bloodPressure, Integer heartRate,
                         Double temperature, Double weight, Double height, String labTestResults,
                         String imagingReports, String prescriptions, String treatmentPlan,
                         LocalDateTime admissionDate, LocalDateTime dischargeDate, String followUpDetails,
                         String attendingDoctorName, String departmentWard, String insuranceDetails,
                         String paymentInformation, String consentForms, String doctorSignature,
                         LocalDateTime createdAt, LocalDateTime lastUpdated) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.dob = dob;
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactNumber = emergencyContactNumber;
        this.relationshipToPatient = relationshipToPatient;
        this.medicalHistory = medicalHistory;
        this.surgicalHistory = surgicalHistory;
        this.familyMedicalHistory = familyMedicalHistory;
        this.allergies = allergies;
        this.currentMedications = currentMedications;
        this.vaccinationHistory = vaccinationHistory;
        this.currentSymptoms = currentSymptoms;
        this.diagnosis = diagnosis;
        this.doctorNotes = doctorNotes;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.weight = weight;
        this.height = height;
        this.labTestResults = labTestResults;
        this.imagingReports = imagingReports;
        this.prescriptions = prescriptions;
        this.treatmentPlan = treatmentPlan;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.followUpDetails = followUpDetails;
        this.attendingDoctorName = attendingDoctorName;
        this.departmentWard = departmentWard;
        this.insuranceDetails = insuranceDetails;
        this.paymentInformation = paymentInformation;
        this.consentForms = consentForms;
        this.doctorSignature = doctorSignature;
        this.createdAt = createdAt;
        this.lastUpdated = lastUpdated;
    }

    // Getters and Setters

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }

    public String getEmergencyContactNumber() { return emergencyContactNumber; }
    public void setEmergencyContactNumber(String emergencyContactNumber) { this.emergencyContactNumber = emergencyContactNumber; }

    public String getRelationshipToPatient() { return relationshipToPatient; }
    public void setRelationshipToPatient(String relationshipToPatient) { this.relationshipToPatient = relationshipToPatient; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    public String getSurgicalHistory() { return surgicalHistory; }
    public void setSurgicalHistory(String surgicalHistory) { this.surgicalHistory = surgicalHistory; }

    public String getFamilyMedicalHistory() { return familyMedicalHistory; }
    public void setFamilyMedicalHistory(String familyMedicalHistory) { this.familyMedicalHistory = familyMedicalHistory; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    public String getCurrentMedications() { return currentMedications; }
    public void setCurrentMedications(String currentMedications) { this.currentMedications = currentMedications; }

    public String getVaccinationHistory() { return vaccinationHistory; }
    public void setVaccinationHistory(String vaccinationHistory) { this.vaccinationHistory = vaccinationHistory; }

    public String getCurrentSymptoms() { return currentSymptoms; }
    public void setCurrentSymptoms(String currentSymptoms) { this.currentSymptoms = currentSymptoms; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getDoctorNotes() { return doctorNotes; }
    public void setDoctorNotes(String doctorNotes) { this.doctorNotes = doctorNotes; }

    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

    public Integer getHeartRate() { return heartRate; }
    public void setHeartRate(Integer heartRate) { this.heartRate = heartRate; }

    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public String getLabTestResults() { return labTestResults; }
    public void setLabTestResults(String labTestResults) { this.labTestResults = labTestResults; }

    public String getImagingReports() { return imagingReports; }
    public void setImagingReports(String imagingReports) { this.imagingReports = imagingReports; }

    public String getPrescriptions() { return prescriptions; }
    public void setPrescriptions(String prescriptions) { this.prescriptions = prescriptions; }

    public String getTreatmentPlan() { return treatmentPlan; }
    public void setTreatmentPlan(String treatmentPlan) { this.treatmentPlan = treatmentPlan; }

    public LocalDateTime getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDateTime admissionDate) { this.admissionDate = admissionDate; }

    public LocalDateTime getDischargeDate() { return dischargeDate; }
    public void setDischargeDate(LocalDateTime dischargeDate) { this.dischargeDate = dischargeDate; }

    public String getFollowUpDetails() { return followUpDetails; }
    public void setFollowUpDetails(String followUpDetails) { this.followUpDetails = followUpDetails; }

    public String getAttendingDoctorName() { return attendingDoctorName; }
    public void setAttendingDoctorName(String attendingDoctorName) { this.attendingDoctorName = attendingDoctorName; }

    public String getDepartmentWard() { return departmentWard; }
    public void setDepartmentWard(String departmentWard) { this.departmentWard = departmentWard; }

    public String getInsuranceDetails() { return insuranceDetails; }
    public void setInsuranceDetails(String insuranceDetails) { this.insuranceDetails = insuranceDetails; }

    public String getPaymentInformation() { return paymentInformation; }
    public void setPaymentInformation(String paymentInformation) { this.paymentInformation = paymentInformation; }

    public String getConsentForms() { return consentForms; }
    public void setConsentForms(String consentForms) { this.consentForms = consentForms; }

    public String getDoctorSignature() { return doctorSignature; }
    public void setDoctorSignature(String doctorSignature) { this.doctorSignature = doctorSignature; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "patientId='" + patientId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", emergencyContactNumber='" + emergencyContactNumber + '\'' +
                ", relationshipToPatient='" + relationshipToPatient + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                ", surgicalHistory='" + surgicalHistory + '\'' +
                ", familyMedicalHistory='" + familyMedicalHistory + '\'' +
                ", allergies='" + allergies + '\'' +
                ", currentMedications='" + currentMedications + '\'' +
                ", vaccinationHistory='" + vaccinationHistory + '\'' +
                ", currentSymptoms='" + currentSymptoms + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", doctorNotes='" + doctorNotes + '\'' +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", heartRate=" + heartRate +
                ", temperature=" + temperature +
                ", weight=" + weight +
                ", height=" + height +
                ", labTestResults='" + labTestResults + '\'' +
                ", imagingReports='" + imagingReports + '\'' +
                ", prescriptions='" + prescriptions + '\'' +
                ", treatmentPlan='" + treatmentPlan + '\'' +
                ", admissionDate=" + admissionDate +
                ", dischargeDate=" + dischargeDate +
                ", followUpDetails='" + followUpDetails + '\'' +
                ", attendingDoctorName='" + attendingDoctorName + '\'' +
                ", departmentWard='" + departmentWard + '\'' +
                ", insuranceDetails='" + insuranceDetails + '\'' +
                ", paymentInformation='" + paymentInformation + '\'' +
                ", consentForms='" + consentForms + '\'' +
                ", doctorSignature='" + doctorSignature + '\'' +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}

