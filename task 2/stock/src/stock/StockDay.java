package stock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class StockDay implements Comparable < StockDay > {

    // Example data:
    // Date       Open  High  Low  Close
    // 31/10/2016 58.25 58.65 58.2 58.35

    // ---------|  Members  |------------------------
    private LocalDate date;
    private BigDecimal open, high, low, close;
    private Boolean isHammer;

    // ---------|  Constructors  |------------------------
    public StockDay ( final LocalDate localDateArg , final BigDecimal openArg , final BigDecimal highArg , final BigDecimal lowArg , final BigDecimal closeArg ) {
        // In real work, add defensive code to validate data such as no nulls, only positive numbers, reasonable dates.
        this.date = localDateArg;
        this.open = openArg;
        this.high = highArg;
        this.low = lowArg;
        // Verify the high is greater than or equal to the low.
        if ( this.high.compareTo( this.low ) < 0 ) {
            throw new IllegalArgumentException( "The passed High is below the passed Low for Date of " + this.date + ". Not possible." );
        }
        this.close = closeArg;
        this.isHammer = this.determineHammer();
    }

    private Boolean determineHammer () {
        // A hammer is a price pattern in candlestick charting that occurs when a security trades significantly lower than its opening, but rallies later in the day to close either above or near its opening price. This pattern forms a hammer-shaped candlestick, in which the body is at least half the size of the tail or wick.
        // Read more: Hammer https://www.investopedia.com/terms/h/hammer.asp#ixzz5G6rqtbkv
        // See also: http://www.onlinetradingconcepts.com/TechnicalAnalysis/Candlesticks/Hammer.html

        // Caveat: This code is a quick rough draft, not thought-through, and totally untested. Use at your own risk. For demonstration purposes only.

        // First check if the High is above the Close. A Hammer has little or no upper  "shadow" (line protruding above the box). We'll go with "no shadow" for simplicity here.
        if ( this.high.compareTo( this.close ) > 0 ) { // if high > close, not a hammer.
            return Boolean.FALSE;
        }

        // Proceed with next check: Is "tail" (lower shadow) at least twice as long as height of box.
        BigDecimal closeOpenDeltaAbsolute_BoxHeight = this.close.subtract( this.open ).abs();
        BigDecimal lowerOfCloseOrOpen = ( this.close.compareTo( this.open ) < 0 ) ? this.close : this.open;  // If x is smaller than y, use x. If x is greater than or equal to y, use y.
        BigDecimal lowerShadowHeight = lowerOfCloseOrOpen.subtract( this.low );
        // A Hammer has a long lower shadow (delta between either Close or Open, whichever is lower, and the Low), at least twice as tall as the box (Close-Open absolute delta).
        BigDecimal requiredMinimumLengthFactorOfShadow = new BigDecimal( "2" );
        BigDecimal doubleCloseOpenDeltaAbsolute = closeOpenDeltaAbsolute_BoxHeight.multiply( requiredMinimumLengthFactorOfShadow );
        Boolean hammer = ( lowerShadowHeight.compareTo( doubleCloseOpenDeltaAbsolute ) > 0 );
        return hammer;

    }


    // ---------|  Accessors  |------------------------
    // All fields are read-only. Just getters, no setters.
    public LocalDate getDate () {
        return this.date;
    }

    public BigDecimal getOpen () {
        return this.open;
    }

    public BigDecimal getHigh () {
        return this.high;
    }

    public BigDecimal getLow () {
        return this.low;
    }

    public BigDecimal getClose () {
        return this.close;
    }

    public Boolean isHammer () {
        return this.isHammer;
    }

    // ---------|  Override `Object`  |------------------------
    @Override
    public String toString () {
        return "StockDay{ " +
                "date=" + this.date +
                ", open=" + this.open +
                ", high=" + this.high +
                ", low=" + this.low +
                ", close=" + this.close +
                ", isHammer=" + this.isHammer +
                " }";
    }

    @Override
    public boolean equals ( final Object oArg ) {
        if ( this == oArg ) return true;
        if ( oArg == null || getClass() != oArg.getClass() ) return false;
        final StockDay stockDay = ( StockDay ) oArg;
        return Objects.equals( this.date , stockDay.date ) &&
                Objects.equals( this.open , stockDay.open ) &&
                Objects.equals( this.high , stockDay.high ) &&
                Objects.equals( this.low , stockDay.low ) &&
                Objects.equals( this.close , stockDay.close );
        // Perhaps this should be coded to only consider the `LocalDate` field alone.
    }

    @Override
    public int hashCode () {
        return Objects.hash( this.date , this.open , this.high , this.low , this.close );
        // Perhaps this should be coded to only consider the `LocalDate` field alone.
    }


    @Override
    public int compareTo ( final StockDay o ) {
        // Compare the date field only.
        int result = this.getDate().compareTo( o.getDate() );
        return result;
    }
}
