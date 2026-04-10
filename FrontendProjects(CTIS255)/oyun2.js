// --- Değişkenler ---
let skor = 0;
let kalanSure = 10; // Oyun süresi 10 saniye
let oyunAktifMi = false;
let tiklamaPuani = 10; // Başlangıç puan
let puanInterval;
let oyunInterval;

// DOM Elementleri
const kapak = document.getElementById("kapak-sayfasi");
const geriSayimEkrani = document.getElementById("geri-sayim");
const sayacMetni = document.getElementById("sayac-metni");
const oyunAlani = document.getElementById("oyun-alani");
const izgara = document.getElementById("izgara");
const skorYazi = document.getElementById("skor");
const zamanYazi = document.getElementById("zaman");
const hiScoreYazi = document.getElementById("en-yuksek-skor");
const cubuk = document.getElementById("puan-cubugu");
const icMesaj = document.getElementById("oyun-ici-mesaj");
const bitisEkrani = document.getElementById("oyun-sonu-mesaji");
const sonucYazi = document.getElementById("sonuc-basligi");

// LocalSToragedan en yüksek skoru alma kısmı
let hiScore = localStorage.getItem("hiScore") || 0;
hiScoreYazi.textContent = hiScore; 


// Kapak sayfasına tıklama
kapak.addEventListener("click", function() {
    kapak.classList.add("gizli"); 
    hazirlikBaslat();
});


function hazirlikBaslat() {
    geriSayimEkrani.classList.remove("gizli"); 
    let sn = 3;
    sayacMetni.textContent = sn;

    // Geri sayım sayacı
    let hazirlikTimer = setInterval(function() {
        sn--;
        if (sn > 0) {
            sayacMetni.textContent = sn;
        } else {
            clearInterval(hazirlikTimer); // [cite: 155]
            geriSayimEkrani.classList.add("gizli");
            oyunuBaslat();
        }
    }, 1000);
}

function oyunuBaslat() {
    oyunAlani.classList.remove("gizli");
    oyunAktifMi = true;

    // "Tap the black tiles"
    setTimeout(function() {
        if (icMesaj) icMesaj.classList.add("gizli");
    }, 1000);

    izgaraOlustur();
    
    // Başlangıçta 3 siyah kare ekle
    for(let i=0; i<3; i++) {
        yeniKareEkle();
    }

    // Oyun süresi sayma ksımı
    oyunInterval = setInterval(function() {
        kalanSure--;
        zamanYazi.textContent = kalanSure;
        if (kalanSure <= 0) {
            oyunuBitir();
        }
    }, 1000);

    cubukZamanlayicisiBaslat();
}

function izgaraOlustur() {
    izgara.innerHTML = ""; // İçini temizlme
    for (let i = 0; i < 16; i++) {
        const hucre = document.createElement("div"); 
        hucre.classList.add("hucre");
        hucre.dataset.idx = i; // data-
        hucre.addEventListener("click", kareyeTikla);
        izgara.append(hucre);
    }
}

function yeniKareEkle() {
    const hucres = document.querySelectorAll(".hucre");
    let boslar = [];
    hucres.forEach(function(h) {
        if (!h.classList.contains("siyah-kare")) {
            boslar.push(h);
        }
    });
    
    if (boslar.length > 0) {
        let secilen = boslar[Math.floor(Math.random() * boslar.length)];
        secilen.classList.add("siyah-kare"); // [cite: 1177]
    }
}

function kareyeTikla(e) {
    if (!oyunAktifMi) return;
    const el = e.target; //tiklama

    if (el.classList.contains("siyah-kare")) {
        // Puanı güncelleme
        skor += tiklamaPuani;
        skorYazi.textContent = skor;
        
        // görsel efektler
        el.classList.remove("siyah-kare");
        el.classList.add("yesil-kare");
        el.textContent = "+" + tiklamaPuani;

        // temizleme
        setTimeout(function() {
            el.classList.remove("yesil-kare");
            el.textContent = "";
        }, 200);

        yeniKareEkle();
        cubukZamanlayicisiBaslat(); // Çubuğu sıfırlama
    }
}

function cubukZamanlayicisiBaslat() {
    clearInterval(puanInterval);
    tiklamaPuani = 10;
    let genislik = 100;
    cubuk.style.width = "100%"; //doldurmak icin

    puanInterval = setInterval(function() {
        tiklamaPuani--;
        genislik -= 10;
        
        if (genislik < 0) genislik = 0;
        cubuk.style.width = genislik + "%";
        if (tiklamaPuani <= 0) {
            tiklamaPuani = 1; // Minimum puan 1 olsun
            clearInterval(puanInterval);
        }
    }, 150);
}

function oyunuBitir() {
    oyunAktifMi = false;
    clearInterval(oyunInterval);
    clearInterval(puanInterval);

    // High score bakmak icin
    if (skor > hiScore) {
        hiScore = skor;
        localStorage.setItem("hiScore", hiScore);
        sonucYazi.textContent = "New High Score!";
        sonucYazi.style.color = "green";

        // Konfeti
        confetti({
            particleCount: 150,
            spread: 70,
            origin: { y: 0.6 }
        });
    } else {
        sonucYazi.textContent = "Time is up";
        sonucYazi.style.color = "red";
    }

    bitisEkrani.classList.remove("gizli");
}