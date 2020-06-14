const dateToString = (date) => {
        var month = date.getMonth() + 1;
        var day = date.getDate();
        month =  (month < 10)? "0" + month.toString() : month.toString();
        day = (day < 10)? "0" + day.toString() : day.toString();
        var year = date.getFullYear();
        return day + "-" + month + "-" + year;
    };

const dateToTerm = (date) => {
        var hour = date.getHours();
        var minute = date.getMinutes();
        hour =  (hour < 10)? "0" + hour.toString() : hour.toString();
        minute = (minute < 10)? "0" + minute.toString() : minute.toString();
        return hour + ":" + minute;        
    };
const dateToFullTerm = (date) => {
        return dateToTerm(date) + " " + dateToString(date); 
    };
const dateToPickerString = (date) => {
    return date.toISOString().split("T")[0];
}
export default {dateToString, dateToTerm, dateToFullTerm, dateToPickerString};
