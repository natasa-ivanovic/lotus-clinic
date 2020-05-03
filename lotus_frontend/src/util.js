export const util = {
    dateToString: (date) => {
        return date.split("T")[0];
    },
    /*refreshToken: () => {
        fetch(this.$apiURL + "/auth/refresh", {headers: {'Authorization': this.$authKey }})
            .then(response => {
                return response.json();
            })
            .then(response => {
                if (response.status == 200) {
                    this.$authKey = response.accesToken;
                    return true;
                } else 
                    return false;
            })
            .except(()) => {
                return false;
            })
    },*/
    /*logout: function() {
        console.log(this.$role);
        console.log(this.$authKey);
        this.$role = null;
        this.$authKey = null;
        console.log(this.$role);
        console.log(this.$authKey);
        localStorage.removeItem('authKey');
        localStorage.removeItem('role');
        router.push({ name: "login" })
      }*/
}