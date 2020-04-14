export const util = {
    dateToString: (date) => {
        /* var month = date.getMonth() + 1;
        if (month < 10)
            month = "0" + month;
        var day = date.getDate();
        if (day < 10)
            day = "0" + day;
        return date.getFullYear() + "-" + month + "-" + day; */
        return date.split("T")[0];
    }
}