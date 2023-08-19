package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.NameNotFoundException;

import java.util.Optional;
import java.util.Set;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.EventData;
import com.example.demo.dao.LoginData;
import com.example.demo.dao.TransactionData;
import com.example.demo.dto.PaymentResponse;
import com.example.demo.dto.PdfResponse;
import com.example.demo.model.Eventdetail;
import com.example.demo.model.OrderFormat;
import com.example.demo.model.TransactionDetails;
import com.example.demo.model.userinfo;
import com.example.demo.optimizationServices.PdfService;
import com.example.demo.optimizationServices.TransactionHistory;
import com.example.demo.services.PaymentGatewayService;
import com.google.zxing.WriterException;
import com.razorpay.Order;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/payment")
public class Payment_controller {

    @Autowired
    EventData eventData;

    @Autowired
    LoginData loginData;

    @Autowired
    PaymentGatewayService paymentGatewayService;

    @Autowired
    TransactionData transactionData;

    @Autowired
    TransactionHistory transactionHistory;

    @Autowired
    private PdfService pdfService;

    @PostMapping("/Transaction")
    public OrderFormat TransactionProcess(@RequestBody PaymentResponse paymentResponse) throws Exception {
        List<Eventdetail> eventdetails = eventData.findAll();

        if (loginData.existsById(paymentResponse.getUserId())) {
            for (Eventdetail details : eventdetails) {
                if (details.getEventId().equalsIgnoreCase(paymentResponse.getEventId())) {
                    PaymentGatewayService paymentGatewayService = new PaymentGatewayService();
                    OrderFormat oo = paymentGatewayService
                            .createTransaction(details.getPrice() * paymentResponse.getFrequency());
                    return oo;
                }
            }

        }
        return null;

    }

    @PostMapping("/PaymentDetails")
    public String SavePaymentDetails(@RequestBody TransactionDetails transactionDetails) {
        transactionData.save(transactionDetails);

        return "Transaction details saved successfully";
    }

    @GetMapping("/PayHistory/{id}")
    public List<TransactionDetails> GetTransacacionHistory(@PathVariable String id) {

        List<TransactionDetails> data = transactionHistory.getTransactionDetails(id);

        return data;
    }

    // @PostMapping(value = "/GetTicket", produces =
    // MediaType.APPLICATION_PDF_VALUE)
    @PostMapping("/GetTicket")
    public ResponseEntity<byte[]> downloadTicket(@RequestBody PdfResponse pdfResponse)
            throws IOException, NameNotFoundException, WriterException {

        // List<Eventdetail> eventList = eventData.findAll();
        List<byte[]> data = new ArrayList<>();
        Optional<TransactionDetails> list2 = transactionData.findById(pdfResponse.gettId());
        Optional<userinfo> userList = loginData.findById(pdfResponse.getUserId());

        // Map<String, String> map = new HashMap<String, String>();
        Optional<Eventdetail> listt = eventData.findById(list2.get().getEventId());

        String Name = userList.get().getName();
        String Event_ID = list2.get().getEventId();
        String Event_Name = list2.get().getEventName();
        String Location = "";
        String Amount = Long.toString(list2.get().getAmount());
        String Order_Id = list2.get().getRazorpay_order_id();
        String Count = Long.toString(list2.get().getTickets());
        if (listt.get().getEventVenue().length() == 0) {
            Location = Location + "Online";

        } else {
            Location = Location + listt.get().getEventVenue();
        }

        byte[] pdfArr = pdfService.getTicket(Name, Amount, Location, Event_ID, Event_Name,
                Order_Id, Count);
        data.add(pdfArr);

        // return data;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("your_pdf_filename.pdf")
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfArr);

    }

}
