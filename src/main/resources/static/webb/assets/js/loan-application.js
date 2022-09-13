const { createApp } = Vue

createApp({
    data() {
        return {
            clients: {},
            currentClient: {},
            accounts: [],
            activeAccounts: "",

            loans: [],
            loansType: [],
            loanNameChoose: [],
            loanMortgage: [],
            loanPersonal: [],
            loanCar: [],
        

            loanPayments: "",
            amount: "",
            accountNumberDestiny: "",

            activeAccounts: "",
            accountNumber: "",

            totalWithTax: "",
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
                    this.activeAccounts = this.accounts.filter(a => a.accountActive)
                    this.loans = this.clients.loans;
                    this.cards = this.clients.cards;
                    this.normalizeDate(this.cards)
                    this.loadLoans();

                }
                )
        }, normalizeDate(card) {
            card.forEach(card => {
                card.fromDate = card.fromDate.slice(2, 7)
                card.thruDate = card.thruDate.slice(2, 7)
            });
        }, loadLoans() {
            axios.get('/api/loans')
                .then(res => {
                    this.loansType = res.data;
                    this.loanMortgage = this.loansType.filter(loan => loan.name == "Mortgage")
                    this.loanCar = this.loansType.filter(loan => loan.name == "Personal")
                    this.loanPersonal = this.loansType.filter(loan => loan.name == "Car")
                    console.log(this.loansType)
                    console.log(this.loanMortgage)
                    console.log(this.loanCar)
                    console.log(this.loanPersonal)
                })
        }, getLoan() {
            const swalWithBootstrapButtons = Swal.mixin({
                customClass: {
                    confirmButton: 'btn btn-success',
                    cancelButton: 'btn btn-danger'
                },
                buttonsStyling: false
            })
            swalWithBootstrapButtons.fire({
                title: 'Are you sure?',
                text: "You are about to create a new account!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes, Create it!',
                cancelButtonText: 'No, cancel!',
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/loans', { nameLoan: this.loanNameChoose, amount: this.amount, payments: this.loanPayments, accountNumberDestiny: this.accountNumberDestiny })
                    swalWithBootstrapButtons.fire(
                        'Deleted!',
                        'your account was successfully created',
                        'success'
                    )
                        .then(() => window.location.reload())
                } else if (
                    /* Read more about handling dismissals below */
                    result.dismiss === Swal.DismissReason.cancel
                ) {
                    swalWithBootstrapButtons.fire(
                        'Cancelled',
                        'Your imaginary file is safe :)',
                        'error'
                    )
                }
            })

                .then(() => window.location.href = '/webb/pages/loans.html')
        }
    },
    computed: {
        totalInstallments() {
            if (this.loanNameChoose == 'Car' && this.loanPayments == 6) {
                this.totalWithTax = ((this.amount * 1.25) / this.loanPayments)
            }
            if (this.loanNameChoose == 'Car' && this.loanPayments == 12) {
                this.totalWithTax = ((this.amount * 1.30) / this.loanPayments)
            }
            if (this.loanNameChoose == 'Car' && this.loanPayments == 24) {
                this.totalWithTax = ((this.amount * 1.35) / this.loanPayments)
            }
            if (this.loanNameChoose == 'Car' && this.loanPayments == 36) {
                this.totalWithTax = ((this.amount * 1.40) / this.loanPayments)
            }
            if (this.loanNameChoose == 'Personal' && this.loanPayments == 12) {
                this.totalWithTax = ((this.amount * 1.25) / this.loanPayments)
            }
            if (this.loanNameChoose == 'Personal' && this.loanPayments == 24) {
                this.totalWithTax = ((this.amount * 1.30) / this.loanPayments)
            }
            if (this.loanNameChoose == 'Mortgage' && this.loanPayments == 12) {
                this.totalWithTax = ((this.amount * 1.25) / this.loanPayments)
            }
            if (this.loanNameChoose == 'Mortgage' && this.loanPayments == 24) {
                this.totalWithTax = ((this.amount * 1.30) / this.loanPayments)
            }
            if (this.loanNameChoose == 'Mortgage' && this.loanPayments == 36) {
                this.totalWithTax = ((this.amount * 1.40) / this.loanPayments)
            }
            if (this.loanNameChoose == 'Mortgage' && this.loanPayments == 48) {
                this.totalWithTax = ((this.amount * 1.45) / this.loanPayments)
            }
            if (this.loanNameChoose == 'Mortgage' && this.loanPayments == 60) {
                this.totalWithTax = ((this.amount * 1.50) / this.loanPayments)

            }
            return Math.round(this.totalWithTax)
        }
    }
}


).mount('#app')