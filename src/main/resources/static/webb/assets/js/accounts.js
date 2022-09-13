const { createApp } = Vue

createApp({
    data() {
        return {
            clients: {},
            currentClient: {},
            accounts: [],
            loans: [],
            cards: [],
            accountsClients: [],
            queryString: "",
            params: "",
            id: "",

            email: "",

        }
    },
    created() {
        this.loadClientList();

    },
    methods: {
        loadClientList() {
            axios.get('/api/clients/current')
                .then(res => {
                    this.clients = res.data;
                    this.accounts = this.clients.accounts;
                    this.loans = this.clients.loans;
                    this.cards = this.clients.cards;
                    this.normalizeDate(this.accounts)
                }
                )
        }, normalizeDate(date) {
            date.forEach(transaction => {
                transaction.creationDate = transaction.creationDate.slice(0, 10)
            });
        }, createAccount() {
            axios.post("/api/clients/current/accounts", `email=${this.clients.email}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                .then(() => Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Your work has been saved',
                    showConfirmButton: false,
                    timer: 1500
                }))
                .then(() => window.location.reload())
        }, logout() {
            axios.post('/api/logout')
                .then(() => Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Good bye! <3 ',
                    showConfirmButton: false,
                    timer: 1500
                }))
                .then(() =>
                    window.location.href = '/webb/pages/index.html/')
            console.log("logout successful")
        }
    },
}
).mount('#app')

/*----------------------ESTILOS--------------------- */
// ===== NAVBAR ===== //
$(window).scroll(function () {

    let position = $(this).scrollTop();
    if (position >= 100) {
        $('.nav-menu').addClass('costum-navbar');
    } else {
        $('.nav-menu').removeClass('costum-navbar');
    }

});


// ===== HAMBURGER BUTTON ===== //
$(document).ready(function () {

    $('.nav-button').click(function () {
        $('.nav-button').toggleClass('change');
    })

});
