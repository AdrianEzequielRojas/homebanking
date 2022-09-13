const { createApp } = Vue

createApp({
    data() {
        return {
            clients: {},
            accounts: [],
            loans: [],
            cards: [],
            accountsClients: [],
            queryString: "",
            params: "",
            id: "",
            transactions: [],
        }
    },
    created() {
        this.queryString = location.search;
        this.params = new URLSearchParams(this.queryString);
        this.id = this.params.get('id');
        this.loadClientList()
        this.loadTransactions();

    },
    methods: {
        loadClientList() {
            axios.get('/api/clients/' + this.id)
                .then(res => {
                    this.clients = res.data;
                    this.accounts = this.clients.accounts;
                    this.loans = this.clients.loans;
                    this.cards = this.clients.cards;
                    this.normalizeDate(this.accounts)
                    console.log(this.clients)
                    console.log(this.accounts)
                    console.log(this.loans)
                    console.log(this.cards)
                }
                )
        }, normalizeDate(date) {
            date.forEach(transaction => {
                transaction.creationDate = transaction.creationDate.slice(0, 10)
            });
        }, loadTransactions() {
            axios.get("/api/accounts/" + this.id)
                .then(res => {
                    this.account = res.data;
                    this.transactions = this.account.transaction;
                    console.log(this.transactions)
                }
                )
        }
    }
}).mount('#app')


/* ESTILOS */


$('.first #removeCard').click(function () {
    toggleReveal($('.first .action'), $('.first .confirm'));
});

$('.first #cancelDelete').click(function () {
    toggleReveal($('.first .confirm'), $('.first .action'));
});

function toggleReveal($hideElement, $showElement) {
    $hideElement.removeClass('reveal');

    setTimeout(function () {
        $hideElement.hide();
        $showElement.show().addClass('reveal');
    }, 200);
}


/*active button class onclick*/
$("nav a").click(function (e) {
    e.preventDefault();
    $("nav a").removeClass("active");
    $(this).addClass("active");
    if (this.id === !"payment") {
        $(".payment").addClass("noshow");
    } else if (this.id === "payment") {
        $(".payment").removeClass("noshow");
        $(".rightbox").children().not(".payment").addClass("noshow");
    } else if (this.id === "profile") {
        $(".profile").removeClass("noshow");
        $(".rightbox").children().not(".profile").addClass("noshow");
    } else if (this.id === "subscription") {
        $(".subscription").removeClass("noshow");
        $(".rightbox").children().not(".subscription").addClass("noshow");
    } else if (this.id === "privacy") {
        $(".privacy").removeClass("noshow");
        $(".rightbox").children().not(".privacy").addClass("noshow");
    } else if (this.id === "settings") {
        $(".settings").removeClass("noshow");
        $(".rightbox").children().not(".settings").addClass("noshow");
    }
});

function w3_open() {
    document.getElementById("mySidebar").style.display = "block";
    document.getElementById("myOverlay").style.display = "block";
}

function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
    document.getElementById("myOverlay").style.display = "none";
}

// Modal Image Gallery
function onClick(element) {
    document.getElementById("img01").src = element.src;
    document.getElementById("modal01").style.display = "block";
    var captionText = document.getElementById("caption");
    captionText.innerHTML = element.alt;
}


// ===== NAVBAR ===== //
$(window).scroll(function () {

    let position = $(this).scrollTop();
    if (position >= 100) {
        $('.nav-menu').addClass('costum-navbar');
    } else {
        $('.nav-menu').removeClass('costum-navbar');
    }

});

$(window).scroll(function () {

    let position = $(this).scrollTop();
    if (position >= 100) {
        $('.nav-img').addClass('costum-img');
    } else {
        $('.nav-img').removeClass('costum-img');
    }

});


// ===== HAMBURGER BUTTON ===== //
$(document).ready(function () {

    $('.nav-button').click(function () {
        $('.nav-button').toggleClass('change');
    })

});

