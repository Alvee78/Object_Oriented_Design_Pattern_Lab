// ======================================================================
//  Entity: Fine
//  Attributes : fineID, borrowID, amount, overdueDays, paymentStatus
//  Methods    : calculateFine, updateFine, markAsPaid
// ======================================================================

public class Fine {

    private String fineId;
    private String borrowId;
    private int    overdueDays;
    private double amount;
    private String paymentStatus;

    /**
     * @param fineId         unique fine ID
     * @param borrowRecord   the overdue borrow record
     * @param fineStrategy   Strategy Pattern — determines the fine rate
     */
    public Fine(String fineId, BorrowRecord borrowRecord, FineStrategy fineStrategy) {
        this.fineId        = fineId;
        this.borrowId      = borrowRecord.getBorrowId();
        this.overdueDays   = borrowRecord.calculateOverdueDays();
        this.amount        = fineStrategy.calculateFine(this.overdueDays);
        this.paymentStatus = "Unpaid";
    }

    public void updateFine(BorrowRecord borrowRecord, FineStrategy fineStrategy) {
        this.overdueDays = borrowRecord.calculateOverdueDays();
        this.amount      = fineStrategy.calculateFine(this.overdueDays);
    }

    public void markAsPaid() {
        this.paymentStatus = "Paid";
        System.out.println("[Fine] Fine " + fineId + " marked as paid.");
    }

    // ── Getters ────────────────────────────────────────────────────
    public String getFineId()        { return fineId; }
    public String getBorrowId()      { return borrowId; }
    public int    getOverdueDays()   { return overdueDays; }
    public double getAmount()        { return amount; }
    public String getPaymentStatus() { return paymentStatus; }

    @Override
    public String toString() {
        return "Fine(id=" + fineId + ", overdue=" + overdueDays +
               " days, amount=" + amount + " BDT, status=" + paymentStatus + ")";
    }
}
