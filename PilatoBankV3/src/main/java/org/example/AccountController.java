package org.example;

import org.example.controller.ApiResponse;
import org.example.controller.TransactionRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    @GetMapping("/status")
    public String checkStatus() {
        return "Pilato Bank API is live and securely running on the web!";
    }

    // deposit method
    @PostMapping("/deposit")
    public ApiResponse<String> deposit(@RequestBody TransactionRequest request) {
        ClientDAO dao = new ClientDAO();
        boolean isSuccess = dao.updateBalance(request.getAccountNumber(), request.getAmount());

        if (isSuccess) {
            return new ApiResponse<>("SUCCESS", "Deposit successful!", null);
        } else {
            return new ApiResponse<>("FAILED", "Deposit failed. Check account number.", null);
        }
    }

    // withdrawal method
    @PostMapping("/withdraw")
    public ApiResponse<String> withdraw(@RequestBody TransactionRequest request) {
        ClientDAO dao = new ClientDAO();
        boolean isSuccess = dao.withdrawFunds(request.getAccountNumber(), request.getAmount());

        if (isSuccess) {
            return new ApiResponse<>("SUCCESS", "Withdrawal successful!", null);
        } else {
            return new ApiResponse<>("FAILED", "Withdrawal failed. Check account number or insufficient funds.", null);
        }
    }

    // Check balance method
    @GetMapping("/balance/{accountNumber}")
    public ApiResponse<Double> getBalance(@PathVariable("accountNumber") String accountNumber) {
        ClientDAO dao = new ClientDAO();

        double balance = dao.getBalance(accountNumber);

        if (balance >= 0) {
            return new ApiResponse<>("SUCCESS", "Balance retrieved.", balance);
        } else {
            return new ApiResponse<>("FAILED", "Account not found.", null);
        }
    }

    // Statement Retriever API
    @GetMapping("/history/{accountNumber}")
    public List<Transaction> getHistory(@PathVariable("accountNumber") String accountNumber) {
        ClientDAO dao = new ClientDAO();
        return dao.getTransactionHistory(accountNumber);
    }
}