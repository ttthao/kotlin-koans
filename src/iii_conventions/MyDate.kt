package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override operator fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other);

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = addTimeIntervals(timeInterval, 1);
operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate = addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.count);


enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(count: Int) = RepeatedTimeInterval(this, count);

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override operator fun contains(d: MyDate): Boolean = (d >= start) &&  (d <= endInclusive);
    override operator fun iterator(): Iterator<MyDate> = DateIterator(this);
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    var current: MyDate = dateRange.start;

    // Return current, set current to next day
    override operator fun next(): MyDate {
        val result: MyDate = current
        current = current.nextDay();
        return result;
    }

    override operator fun hasNext(): Boolean = current <= dateRange.endInclusive;

}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val count: Int);


// My solution for ClosedRange
//class DateRange(val start: MyDate, val endInclusive: MyDate)
//operator fun DateRange.contains(d: MyDate): Boolean {
//    return (d >= start) && (d <= endInclusive);
//}
