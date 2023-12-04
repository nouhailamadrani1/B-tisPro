package com.rentalhive.rentalhive.service.impl;

import com.rentalhive.rentalhive.dto.ContractDTO;
import com.rentalhive.rentalhive.model.Estimate;
import com.rentalhive.rentalhive.model.EstimateStatus;
import com.rentalhive.rentalhive.model.RentalRequest;
import com.rentalhive.rentalhive.repository.ContractRepository;
import com.rentalhive.rentalhive.repository.RentalRequestRepository;
import com.rentalhive.rentalhive.service.ContractServiceInterface;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractServiceInterface {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RentalRequestRepository rentalRequestRepository;

    @Override
    public byte[] generateContractPDF(int id) {
        Estimate estimate = contractRepository.findById(id);

        if (estimate != null && estimate.getEstimateStatus() == EstimateStatus.Approved) {
            return generateContractPDF(createContractDTO(estimate));
        } else {
            return "Estimate not found or not approved".getBytes();
        }
    }

    @Override
    public List<ContractDTO> getAllApprovedEstimatesForClient(int clientId) {
        List<ContractDTO> approvedEstimates = new ArrayList<>();

        List<RentalRequest> rentalRequests = rentalRequestRepository.findRentalRequestsByClientId(clientId);

        for (RentalRequest rentalRequest : rentalRequests) {
            List<Estimate> estimates = rentalRequest.getEstimates();
            for (Estimate estimate : estimates) {
                if (estimate.getEstimateStatus() == EstimateStatus.Approved) {
                    approvedEstimates.add(createContractDTO(estimate));
                }
            }
        }

        return approvedEstimates;
    }

    @Override
    public String archiveContract(int id) {
        Estimate estimate = contractRepository.findById(id);

        if (estimate != null) {
            estimate.setArchived(true);
            contractRepository.save(estimate);
            return "Contrat archivé avec succès.";
        } else {
            return "Contrat non trouvé.";
        }
    }

    @Override
    public List<ContractDTO> getAllNonArchivedEstimatesForClient(int clientId) {
        List<ContractDTO> nonArchivedEstimates = new ArrayList<>();

        List<RentalRequest> rentalRequests = rentalRequestRepository.findRentalRequestsByClientId(clientId);

        for (RentalRequest rentalRequest : rentalRequests) {
            List<Estimate> estimates = rentalRequest.getEstimates();
            for (Estimate estimate : estimates) {
                if (!estimate.isArchived()) {
                    nonArchivedEstimates.add(createContractDTO(estimate));
                }
            }
        }

        return nonArchivedEstimates;
    }

    private byte[] generateContractPDF(ContractDTO contractDTO) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.setLeading(16.5f);

                // Begin the text block
                contentStream.beginText();

                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(contractDTO.getTitle());
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                contentStream.showText("Estimated Cost: $" + contractDTO.getEstimatedCost());
                contentStream.newLine();
                contentStream.showText("Client: " + contractDTO.getClientName());
                contentStream.newLine();

                contentStream.showText("Email: " + contractDTO.getClientEmail());
                contentStream.newLine();
                contentStream.showText("Phone: " + contractDTO.getClientPhone());
                contentStream.newLine();

                contentStream.showText("Equipment: " + contractDTO.getEquipmentName());
                contentStream.newLine();
                contentStream.showText("Equipment Type: " + contractDTO.getEquipmentType());

                // End the text block
                contentStream.endText();

                // Add a separator line
                contentStream.moveTo(100, 650);
                contentStream.lineTo(page.getMediaBox().getWidth() - 100, 650);
                contentStream.stroke();

                contentStream.close();
                document.save(baos);
            }

            document.close();

            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error generating contract PDF", e);
        }
    }

    private ContractDTO createContractDTO(Estimate estimate) {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setTitle("Contract Details");
        contractDTO.setEstimatedCost(String.valueOf(estimate.getEstimatedCost()));
        contractDTO.setClientName(estimate.getRentalRequest().getClient().getName());
        contractDTO.setClientEmail(estimate.getRentalRequest().getClient().getEmail());
        contractDTO.setClientPhone(estimate.getRentalRequest().getClient().getNumberPhone());
        contractDTO.setEquipmentName(estimate.getRentalRequest().getEquipment().getName());
        contractDTO.setEquipmentType(String.valueOf(estimate.getRentalRequest().getEquipment().getEquipmentType()));

        return contractDTO;
    }
}
