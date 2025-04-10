package com.webilovalarishlabchiqish.app.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.webilovalarishlabchiqish.application.databinding.FragmentTestsBinding

class TestsFragment : Fragment() {

    private var _binding: FragmentTestsBinding? = null
    private val binding get() = _binding!!

    private var currentTopicQuestions = listOf<Question>()
    private var currentQuestionIndex = 0
    private var correctAnswerIndex:Int = 0
    private var correctAnswersCount = 0


    private val htmlQuestions = listOf(
        Question("HTML nima?", listOf("Dasturlash tili", "Markap til", "Ma'lumotlar bazasi", "Tizim operatsiyasi"), 1),
        Question("HTML qaysi kompaniya tomonidan ishlab chiqilgan?", listOf("Microsoft", "W3C", "Mozilla", "Google"), 1),
        Question("HTML ning to'liq nomi nima?", listOf("Hyper Text Markup Language", "Hyper Transfer Markup Language", "Hyper Text Machine Language", "Hyper Text Mode Language"), 0),
        Question("HTML5 da yangi qo'shilgan element qaysi?", listOf("<header>", "<body>", "<footer>", "<section>"), 0),
        Question("HTML taglarida atributlarni qanday yozish kerak?", listOf("Ikkita qo'shni tirnoqlar ichida", "Bir qavs ichida", "Bitta qo'sh tirnoq ichida", "Hech qanday atribut yozilmaydi"), 0),
        Question("HTML da 'img' tagi nimani anglatadi?", listOf("Rasm", "Link", "Video", "Audio"), 0),
        Question("HTML dan foydalanishning asosiy maqsadi nima?", listOf("Matnni boshqarish", "Web sahifalar yaratish", "Sahifalarga interaktivlik qo'shish", "Kompyuterlarni dasturlash"), 1),
        Question("HTML da sahifa tarkibini qanday belgilash mumkin?", listOf("Barcha tarkibni <body> tegida", "Barcha tarkibni <html> tegida", "Barcha tarkibni <head> tegida", "Barcha tarkibni <footer> tegida"), 0),
        Question("HTML taglari qanday to'g'ri yoziladi?", listOf("Kichik harf bilan", "Bosh harf bilan", "Aralash harflar bilan", "Yuqori qavs ichida"), 0),
        Question("HTML5 da kiritilgan yangi video tegini ko'rsating", listOf("<video>", "<audio>", "<movie>", "<media>"), 0),
        Question("HTML da <meta> tegining vazifasi nima?", listOf("Sahifaning ma'lumotlari haqida ma'lumot berish", "Sahifaga rasm qo'shish", "Sahifada matnni qirqish", "Sahifa fonini o'zgartirish"), 0)
    )

    private val cssQuestions = listOf(
        Question("CSS nima?", listOf("Stil varag'i", "Dasturlash tili", "HTML teg", "Veb-server"), 0),
        Question("CSS qaysi tilda yoziladi?", listOf("XML", "HTML", "JavaScript", "SASS"), 0),
        Question("CSS da qaysi xususiyat bilan fon rangi o'rnatiladi?", listOf("color", "background-color", "font-size", "text-align"), 1),
        Question("CSS qaysi xususiyat bilan matnni markazlashtirish mumkin?", listOf("text-align", "justify-content", "center-align", "font-align"), 0),
        Question("CSS da elementlarni qaysi xususiyat bilan yonma-yon joylashtirish mumkin?", listOf("flexbox", "grid", "float", "display"), 0),
        Question("CSS da qaysi xususiyat bilan marginni o'zgartirish mumkin?", listOf("padding", "margin", "border", "width"), 1),
        Question("CSS da hover effekti qanday ishlaydi?", listOf("Elementga kursorni olib kelganda", "Elementni bosganda", "Elementga ruju'lanish o'zgartirishida", "Elementning rangi o'zgaradi"), 0),
        Question("CSS da qanday tag yordamida maxsus klasslar yaratiladi?", listOf("<class>", "<css>", "<style>", "<div>"), 2),
        Question("CSS da qaysi xususiyat bilan shrift o'lchamini o'zgartirish mumkin?", listOf("font-size", "font-family", "text-decoration", "font-style"), 0),
        Question("CSS da border radiusni qanday o'zgartirish mumkin?", listOf("border-radius", "border-style", "border-width", "border-color"), 0),
        Question("CSS da qaysi xususiyat yordamida elementlarni vertikal markazlashtirish mumkin?", listOf("align-items", "justify-content", "vertical-align", "text-align"), 0),
        Question("CSS da qaysi xususiyat yordamida elementning orqa fon rasmini qo'shish mumkin?", listOf("background", "background-image", "color", "font"), 1),
        Question("CSS da 'position' xususiyati nima uchun ishlatiladi?", listOf("Elementni sahifada joylashuvini belgilash uchun", "Elementning fonini o'zgartirish uchun", "Textni formatlash uchun", "Elementni o'zgartirish uchun"), 0)
    )

    private val jsQuestions = listOf(
        Question("JavaScript nima?", listOf("Dasturlash tili", "Markap til", "Stil varag'i", "Web-server"), 0),
        Question("JavaScript qaysi vosita bilan bajariladi?", listOf("Veb-brauzer", "Kompyuter", "Mobil telefon", "Veb-server"), 0),
        Question("JavaScriptda funksiyani qanday e'lon qilish mumkin?", listOf("function myFunction() {}", "function: myFunction() {}", "myFunction() = {}", "func myFunction() {}"), 0),
        Question("JavaScriptda o'zgaruvchi qaysi kalit so'z bilan e'lon qilinadi?", listOf("let", "function", "var", "const"), 0),
        Question("JavaScriptda massivni qanday yaratish mumkin?", listOf("[]", "{}", "()", "<>"), 0),
        Question("JavaScriptda operator nima?", listOf("Aritmetik amal", "Funktsiya", "Vazifalar ro'yxati", "Objekt"), 0),
        Question("JavaScriptda '==' va '===' orasidagi farq nima?", listOf("'==' tenglikni tekshiradi, '===' qiymat va turini tekshiradi", "'==' faqat qiymatni tekshiradi, '===' faqat turini tekshiradi", "'==' qiymatni tekshiradi, '===' tenglikni tekshiradi", "Ikkalasi bir xil ishlaydi"), 0),
        Question("JavaScriptda qanday metod bilan massivning oxiriga yangi element qo'shiladi?", listOf("push()", "pop()", "shift()", "unshift()"), 0),
        Question("JavaScriptda 'null' va 'undefined' orasidagi farq nima?", listOf("'null' qiymatni anglatadi, 'undefined' esa o'zgaruvchi e'lon qilingan, lekin qiymat berilmaganligini anglatadi", "'null' va 'undefined' bir xil", "'null' qiymatga teng, 'undefined' esa funksiya natijasi", "'null' faqat obyekt, 'undefined' esa funksiyaga tegishli"), 0),
        Question("JavaScriptda 'for' siklini qanday yozamiz?", listOf("for (let i = 0; i < 10; i++) {}", "for (i = 0; i < 10) {}", "for (i < 10; i++) {}", "for (let i < 10) {}"), 0),
        Question("JavaScriptda DOM nima?", listOf("Document Object Model", "Data Object Model", "Document Online Method", "Direct Object Management"), 0),
        Question("JavaScriptda qanday metod bilan elementga hodisa (event) qo'shiladi?", listOf("addEventListener()", "onClick()", "setEvent()", "bindEvent()"), 0),
        Question("JavaScriptda ob'ektni qanday yaratamiz?", listOf("{}", "[]", "()", "<>"), 0),
        Question("JavaScriptda string (matn)ni qanday to'xtatish mumkin?", listOf("''", "\"\"", "` `", "{}"), 2),
        Question("JavaScriptda 'setTimeout' metodi nima uchun ishlatiladi?", listOf("Kodni belgilangan vaqtga kechiktirish", "Dom elementlarini manipulyatsiya qilish", "Qatorni ulash uchun", "Xatoliklarni ko'rsatish uchun"), 0)
    )

    private val flexboxQuestions = listOf(
        Question("Flexbox nima?", listOf("HTML element", "CSS xususiyati", "JavaScript kutubxonasi", "Web-server"), 1),
        Question("Flexbox qanday xususiyat bilan konteynerga mos keluvchi elementlarni joylashtiradi?", listOf("justify-content", "flex-direction", "display", "align-items"), 2),
        Question("Flexboxda 'flex-direction' xususiyati nima uchun ishlatiladi?", listOf("Elementlarni vertikal yoki gorizontal tartibda joylashtirish", "Konteynerni kattalashtirish", "Elementlarga rang berish", "Elementlarning joylashuvini markazlashtirish"), 0),
        Question("Flexboxda elementlarning o'lchamini moslashtirish uchun qaysi xususiyat ishlatiladi?", listOf("flex-grow", "flex-shrink", "flex-basis", "all of the above"), 3),
        Question("Flexboxda elementlarning vertikal joylashishini boshqarish uchun qaysi xususiyat ishlatiladi?", listOf("align-items", "justify-content", "align-self", "flex-direction"), 0),
        Question("Flexboxda barcha elementlar bir qatorga joylashsa, qaysi xususiyatni qo'llash kerak?", listOf("flex-wrap", "flex-grow", "align-items", "justify-content"), 0),
        Question("Flexboxda elementlarni yuqoriga yoki pastga joylashtirish uchun qaysi xususiyat ishlatiladi?", listOf("align-items", "flex-direction", "justify-content", "align-self"), 0),
        Question("Flexboxda bir nechta qatorda joylashgan elementlarni qanday qilib yangi qatorda joylashtirish mumkin?", listOf("flex-wrap", "flex-basis", "flex-grow", "justify-content"), 0),
        Question("Flexboxda 'flex' qisqacha yozuvi nima?", listOf("flex-grow, flex-shrink, flex-basis", "flex-direction, flex-wrap", "align-items, justify-content", "flex-shrink, align-self"), 0),
        Question("Flexboxda elementlarni markazlashtirish uchun qaysi kombinatsiya ishlatiladi?", listOf("justify-content: center; align-items: center;", "flex-grow: 1; justify-content: center;", "flex-wrap: wrap; align-items: center;", "none of the above"), 0),
        Question("Flexboxda 'align-self' xususiyati nima uchun ishlatiladi?", listOf("Individual elementning joylashishini boshqarish", "Konteynerni moslashtirish", "Elementlarning ko'rinishini sozlash", "Barcha elementlarni teng qilib qo'yish"), 0),
        Question("Flexboxda 'justify-content' xususiyati qanday ishlaydi?", listOf("Elementlar orasidagi bo'sh joyni taqsimlash", "Elementlarni markazlashtirish", "Elementlarni yuqoriga joylashtirish", "Elementlarni shakllantirish"), 0)
    )

    private val gridQuestions = listOf(
        Question("Grid layout nima?", listOf("CSS xususiyati", "Web-server", "Dasturlash tili", "HTML tagi"), 0),
        Question("Grid layoutda qaysi xususiyatlar yordamida qatordan va ustundan joylashuv belgilanadi?", listOf("grid-template-rows, grid-template-columns", "flex-direction, justify-content", "align-items, justify-content", "display, flex-direction"), 0),
        Question("Grid layoutda 'grid-template-areas' xususiyati nima uchun ishlatiladi?", listOf("Qatorlar va ustunlar o'rtasida joylashuvni belgilash", "Elementlar uchun maxsus hududlar yaratish", "Elementlarni moslashtirish", "Barcha elementlarni markazlashtirish"), 1),
        Question("Grid layoutda bir nechta ustunlarga bo'lingan elementlarni qanday joylashtirish mumkin?", listOf("grid-column", "flex-wrap", "justify-content", "align-items"), 0),
        Question("Grid layoutda 'grid-gap' xususiyati nima uchun ishlatiladi?", listOf("Griddagi elementlar orasidagi bo'shliqni belgilash", "Elementlarni markazlashtirish", "Gridning o'lchamini sozlash", "Elementlarning rangini o'zgartirish"), 0),
        Question("Grid layoutda 'grid-auto-rows' xususiyati qanday ishlaydi?", listOf("Qatorlar avtomatik tarzda moslashtiriladi", "Ustunlar avtomatik tarzda moslashtiriladi", "Gridning bo'shliq o'lchamlarini belgilash", "Elementlarning rangi o'zgaradi"), 0),
        Question("Grid layoutda 'grid-template-columns' xususiyati nima uchun ishlatiladi?", listOf("Ustunlar o'lchamini belgilash", "Qatorlar o'lchamini belgilash", "Elementlarning joylashishini markazlashtirish", "Elementlar orasidagi masofani belgilash"), 0),
        Question("Grid layoutda elementlarni bir qatorga joylashtirish uchun qaysi xususiyat ishlatiladi?", listOf("grid-template-columns", "grid-template-rows", "grid-column", "align-items"), 2),
        Question("Grid layoutda 'grid-column' xususiyati qanday ishlaydi?", listOf("Elementni bir nechta ustunga tarqatadi", "Elementni bitta ustunda markazlashtiradi", "Elementni yuqoriga yoki pastga joylashtiradi", "Barcha elementlarni markazlashtiradi"), 0),
        Question("Grid layoutda 'grid-template-rows' xususiyati nima uchun ishlatiladi?", listOf("Qatorlar o'lchamini belgilash", "Ustunlar o'lchamini belgilash", "Griddagi bo'shliqni belgilash", "Elementlarning rangini o'zgartirish"), 0),
        Question("Grid layoutda elementlar qanday ko'rsatiladi?", listOf("grid", "flex", "inline", "block"), 0),
        Question("Grid layoutda elementlar orasidagi bo'shliqni qanday sozlash mumkin?", listOf("grid-gap", "margin", "padding", "border"), 0)
    )

    private val jsDomQuestions = listOf(
        Question("JavaScript DOM nima?", listOf("Ma'lumotlar bazasi", "Dasturlash xususiyati", "HTML hujjatni boshqarish", "Web-server"), 2),
        Question("DOMda 'document.getElementById' usuli nima qiladi?", listOf("Elementni ID orqali tanlaydi", "Elementni URL orqali topadi", "Elementni sinfga qarab tanlaydi", "Elementni o'chiradi"), 0),
        Question("'document.querySelector' usuli nima uchun ishlatiladi?", listOf("Elementni CSS selektori orqali tanlash", "Elementni URL orqali tanlash", "Elementni ID orqali tanlash", "Elementni faqat matn bo'yicha tanlash"), 0),
        Question("DOMda 'addEventListener' usuli nima uchun ishlatiladi?", listOf("Hodisa (event) qo'shish", "Elementni o'chirish", "Elementga klass qo'shish", "Elementning uslubini o'zgartirish"), 0),
        Question("'document.createElement' usuli nima qiladi?", listOf("Yangi HTML element yaratadi", "Elementni o'zgartiradi", "Elementni o'chiradi", "Elementni ko'rsatadi"), 0),
        Question("DOMda 'innerHTML' xususiyati nima uchun ishlatiladi?", listOf("HTML ichidagi matnni o'zgartirish", "CSS stilini o'zgartirish", "Elementni ID orqali topish", "JavaScript kodini bajarish"), 0),
        Question("DOMda 'removeChild' usuli nima qiladi?", listOf("Elementni o'chiradi", "Elementni qo'shadi", "Elementni o'zgartiradi", "Elementni ko'rsatadi"), 0),
        Question("DOMda 'parentNode' xususiyati nima uchun ishlatiladi?", listOf("Elementning ota tugunini olish", "Elementning barcha bolalar tugunlarini olish", "Elementning uslubini o'zgartirish", "Elementga hodisa qo'shish"), 0),
        Question("'document.getElementsByClassName' usuli nima uchun ishlatiladi?", listOf("Sinf nomi orqali elementlarni tanlash", "Elementni ID orqali tanlash", "Elementni o'zgartirish", "Elementni boshqarish"), 0),
        Question("'element.style' xususiyati nima uchun ishlatiladi?", listOf("Elementning uslubini o'zgartirish", "Elementni o'zgartirish", "Elementni o'chirish", "Elementga hodisa qo'shish"), 0),
        Question("DOMda 'setAttribute' usuli nima qiladi?", listOf("Elementga atribut qo'shadi", "Elementni o'chiradi", "Elementni ko'rsatadi", "Elementga rang qo'shadi"), 0),
        Question("DOMda 'textContent' xususiyati nima uchun ishlatiladi?", listOf("Elementning matnini olish yoki o'zgartirish", "Elementning rangini o'zgartirish", "Elementga hodisa qo'shish", "Elementning atributlarini o'zgartirish"), 0)
    )


    private val bootstrapQuestions = listOf(
        Question("Bootstrap nima?", listOf("CSS kutubxonasi", "JavaScript kutubxonasi", "HTML kutubxonasi", "CSS frameworki"), 3),
        Question("Bootstrapning asosiy komponenti nima?", listOf("Navbar", "Button", "Grid", "Card"), 2),
        Question("Bootstrapda 'container' klassi nima uchun ishlatiladi?", listOf("Kontentni markazlashtirish", "Tezroq yuklanishini ta'minlash", "Mobil moslashuvchanlikni ta'minlash", "Kontentni chetga siljitish"), 0),
        Question("Bootstrapda 'grid system' nima?", listOf("Bitta elementga rasm qo'shish", "Elementlarni gorizontal ravishda joylashtirish", "Responsive dizaynni yaratish", "HTML taglarini tahrirlash"), 2),
        Question("Bootstrapda 'btn' klassi nima uchun ishlatiladi?", listOf("Button (tugma) yaratish", "Matnni o'zgartirish", "Rasmni o'zgartirish", "Navigatsiya menyusi yaratish"), 0),
        Question("Bootstrapda 'col-md-4' nima qiladi?", listOf("4 ustunli grid yaratadi", "4px bo'shliq yaratadi", "4 qatorli element yaratadi", "4px border qo'shadi"), 0),
        Question("Bootstrapda 'carousel' komponenti nima uchun ishlatiladi?", listOf("Rasm yoki kontentni aylantirish", "Matnni ko'rsatish", "Qisqa navigatsiyani ko'rsatish", "Video ko'rsatish"), 0),
        Question("Bootstrapda 'modal' komponenti nima?", listOf("Oynali oynani ko'rsatish", "Responsive dizayn yaratish", "Dropdown menyu yaratish", "Navbar qo'shish"), 0),
        Question("Bootstrapda 'dropdown' komponenti nima?", listOf("Menyu qo'shish", "Modal oynani ko'rsatish", "Rasm qo'shish", "Carousel qo'shish"), 0),
        Question("Bootstrapda 'alert' komponenti nima?", listOf("Xabar yoki ogohlantirish ko'rsatish", "Rasm ko'rsatish", "Dropdown menyu yaratish", "Button qo'shish"), 0),
        Question("Bootstrapda 'form-control' klassi nima uchun ishlatiladi?", listOf("Form elementlarini uslubini o'zgartirish", "Formani yuborish", "Formani tekshirish", "Formaga rasm qo'shish"), 0),
        Question("Bootstrapda 'navbar' nima?", listOf("Navigatsiya paneli", "Sahifa o'lchamini o'zgartiruvchi tugma", "Formani yuborish tugmasi", "Yangi sahifa yaratish"), 0),
        Question("Bootstrapda 'jumbotron' komponenti nima?", listOf("Katta ajratilgan kontent bloki", "Resurslarni yuklash", "Matnli xabarlar", "Responsive dizayn uchun qaydlar"), 0)
    )

    private val gitQuestions = listOf(
        Question("Git nima?", listOf("Versiyalarni boshqarish tizimi", "Kod muharriri", "Ma'lumotlar bazasi", "Operatsion tizim"), 0),
        Question("GitHub nima?", listOf("Kodlar uchun hosting platformasi", "Dasturlash muharriri", "Mobil ilova", "Veb-brauzer"), 0),
        Question("Gitda yangi repository yaratish uchun qaysi buyruq ishlatiladi?", listOf("git init", "git start", "git create", "git new"), 0),
        Question("Gitda mavjud fayllarni kuzatish uchun qaysi buyruq ishlatiladi?", listOf("git add", "git track", "git push", "git monitor"), 0),
        Question("Gitda commit yaratish buyrug‘i qaysi?", listOf("git commit -m 'xabar'", "git save", "git write", "git message"), 0),
        Question("Gitda repository holatini ko‘rish uchun qaysi buyruq ishlatiladi?", listOf("git status", "git info", "git log", "git show"), 0),
        Question("Gitda barcha commitlar ro‘yxatini ko‘rish uchun qaysi buyruq ishlatiladi?", listOf("git log", "git history", "git list", "git show"), 0),
        Question("Gitda o‘zgarishlarni GitHub’ga yuborish uchun qaysi buyruq ishlatiladi?", listOf("git push", "git upload", "git send", "git commit"), 0),
        Question("Gitda boshqa repositorydan nusxa olish uchun qaysi buyruq ishlatiladi?", listOf("git clone", "git copy", "git pull", "git fetch"), 0),
        Question("Gitda boshqa foydalanuvchining o‘zgarishlarini olish uchun qaysi buyruq?", listOf("git pull", "git fetch", "git clone", "git update"), 0),
        Question("Gitda branch yaratish buyrug‘i?", listOf("git branch <nom>", "git new-branch", "git start", "git checkout -b"), 0),
        Question("Gitda branchlar orasida o‘tish uchun buyrug‘ingiz?", listOf("git checkout", "git switch", "git move", "git jump"), 0),
        Question("Gitda konfliktlar nima?", listOf("Bir nechta branchda bir xil joyda o‘zgarish bo‘lsa", "Commit nomi noto‘g‘ri bo‘lsa", "Fayl yo‘qolsa", "Push bajarilmasa"), 0)
    )


    private val hostingQuestions = listOf(
        Question("Hosting nima?", listOf("Saytni internetga joylash xizmati", "Ma'lumotlar bazasi", "Internet provayder", "Domen nomi"), 0),
        Question("Domen nima?", listOf("Sayt manzili", "Hosting xizmati", "Server", "Internet tezligi"), 0),
        Question("HTML fayllarni hostingga yuklash uchun nima kerak?", listOf("FTP yoki fayl menejeri", "Kompilyator", "Brauzer", "Antivirus"), 0),
        Question("Eng mashhur bepul hosting xizmatlaridan biri?", listOf("GitHub Pages", "YouTube", "Photoshop", "Android Studio"), 0),
        Question("Web-server vazifasi nima?", listOf("HTTP so‘rovlariga javob berish", "Fayllarni zaxiralash", "Kod yozish", "Ma'lumotlar tahlili"), 0),
        Question("cPanel nima?", listOf("Hosting boshqaruv paneli", "Domen nomi", "Ma'lumotlar bazasi", "Mobil ilova"), 0),
        Question("FTP nima?", listOf("Fayl uzatish protokoli", "Ma'lumotlar bazasi", "Xatoliklarni tekshiruvchi", "Web brauzer"), 0),
        Question("WordPress qayerda ishlaydi?", listOf("Hosting serverda", "Telefon ilovasida", "Kompyuter operatsion tizimida", "Facebook’da"), 0),
        Question("Saytni yuklash tezligi nimaga bog‘liq?", listOf("Hosting sifati va server joylashuvi", "Domen nomi uzunligiga", "HTML fayl nomiga", "Kompyuter ekraniga"), 0),
        Question("Subdomain nima?", listOf("Asosiy domen ichidagi domen", "Yangi hosting turi", "DNS fayli", "Yashirin fayl"), 0)
    )


    private val reactJSQuestions = listOf(
        Question("ReactJS nima?", listOf("JavaScript kutubxonasi", "Ma'lumotlar bazasi", "CSS freymvork", "Server tili"), 0),
        Question("React’da komponentlar qanday turlarga bo‘linadi?", listOf("Functional va Class", "Global va Local", "Static va Dynamic", "Inline va Block"), 0),
        Question("React komponentida `state` nima uchun kerak?", listOf("Komponent holatini boshqarish uchun", "Faylni saqlash uchun", "Styling uchun", "Routing uchun"), 0),
        Question("JSX nima?", listOf("JavaScript + HTML sintaksisi", "CSS fayli", "SQL kodi", "React’ning backend qismi"), 0),
        Question("React’da `props` nima qiladi?", listOf("Komponentga tashqi ma'lumot uzatadi", "DOM’ni yangilaydi", "CSS qo‘shadi", "Routing qiladi"), 0),
        Question("React’da `useState` qayerda ishlatiladi?", listOf("Functional komponentlarda", "Class komponentlarda", "SCSS fayllarda", "Backend serverda"), 0),
        Question("React’da `useEffect` nima uchun kerak?", listOf("Yon effektlarni boshqarish", "CSS qo‘shish", "Komponent nomini o‘zgartirish", "Routing qilish"), 0),
        Question("React Router nima?", listOf("SPA ilovalar uchun navigatsiya tizimi", "Ma'lumotlar bazasi", "Bildirishnoma tizimi", "JavaScript kutubxonasi"), 0),
        Question("Virtual DOM nima qiladi?", listOf("Tezroq UI yangilanishini ta’minlaydi", "Ma'lumotlarni saqlaydi", "Serverni boshqaradi", "Kompilyatsiya qiladi"), 0),
        Question("React’da `key` atributi qachon kerak bo‘ladi?", listOf("Ro‘yxatdagi elementlarni aniqlashda", "CSS qo‘shishda", "State yaratishda", "Funksiya chaqirishda"), 0)
    )


    private val vueJsQuestions = listOf(
        Question("Vue.js nima?", listOf("JavaScript framework", "Ma'lumotlar bazasi", "CSS kutubxonasi", "Server tili"), 0),
        Question("Vue’da komponent nima?", listOf("Qayta ishlatiladigan UI blok", "CSS fayl", "Server funksiyasi", "HTML hujjat"), 0),
        Question("Vue instance qanday yaratiladi?", listOf("new Vue({})", "Vue.create()", "Vue.init()", "new VueApp()"), 0),
        Question("Vue’da `data` nima?", listOf("Komponentdagi ma’lumotlar", "Router yo‘li", "CSS klassi", "Serverga yuboriladigan so‘rov"), 0),
        Question("Vue’da `v-bind` direktivasi nima qiladi?", listOf("Attributega data ulaydi", "Fayl yuklaydi", "Router o‘zgartiradi", "CSS qo‘shadi"), 0),
        Question("Vue’da `v-model` nima uchun ishlatiladi?", listOf("Ikkitomonlama data bog‘lash uchun", "Komponentni chaqirish uchun", "Routing uchun", "CSS style uchun"), 0),
        Question("Vue lifecycle hooks nima?", listOf("Komponent holatlaridagi funksiyalar", "HTTP metodlar", "CSS animatsiyalar", "Database queries"), 0),
        Question("Vue’da computed properties nima qiladi?", listOf("Ma’lumotdan kelib chiqib hisoblab qiymat beradi", "Ma’lumot saqlaydi", "Animatsiya qiladi", "Routingni boshqaradi"), 0),
        Question("Vue CLI nima uchun kerak?", listOf("Loyihani yaratish va boshqarish uchun", "Kod formatlash uchun", "Ma'lumotlar bazasini yaratish uchun", "Serverni sozlash uchun"), 0),
        Question("Vue’da `methods` bo‘limi nima uchun?", listOf("Funksiyalarni aniqlash uchun", "Routingni belgilash uchun", "Style berish uchun", "Event’larni bloklash uchun"), 0)
    )


    private val angularQuestions = listOf(
        Question("Angular nima?", listOf("Front-end framework", "Backend tili", "Ma'lumotlar bazasi", "Brauzer"), 0),
        Question("Angular qaysi til asosida yozilgan?", listOf("TypeScript", "JavaScript", "Python", "Dart"), 0),
        Question("Angular loyihasini yaratish buyrug‘i?", listOf("ng new", "ng create", "angular start", "npm init"), 0),
        Question("Angular komponentining asosiy qismlaridan biri?", listOf("HTML, CSS, TypeScript", "PHP, HTML, CSS", "JS, JSON, XML", "TS, SQL, HTML"), 0),
        Question("Angular’da `@Component` dekoratori nima uchun ishlatiladi?", listOf("Komponentni aniqlash uchun", "Servis yaratish uchun", "Routing qilish uchun", "HTTP so‘rov uchun"), 0),
        Question("Angular’da ma’lumotlar uzatishda ishlatiladigan bog‘lanish turi?", listOf("Data Binding", "Data Mining", "Data Routing", "Data Transfer"), 0),
        Question("Angular CLI bu?", listOf("Buyruqlar orqali boshqarish interfeysi", "Kod muharriri", "Ma'lumotlar ombori", "HTML parser"), 0),
        Question("Angular’da routing nima uchun kerak?", listOf("Sahifalar orasida o‘tish uchun", "Ma'lumot saqlash uchun", "Animatsiya qilish uchun", "CSS ulash uchun"), 0),
        Question("Angular’da `ng serve` buyrug‘i nima qiladi?", listOf("Ilovani ishga tushiradi", "Komponent yaratadi", "Server o‘chiradi", "Routing yaratadi"), 0),
        Question("Angular loyihasida `app.module.ts` fayli nima?", listOf("Bosh modul fayli", "CSS fayli", "Ma’lumotlar bazasi fayli", "Komponent HTML fayli"), 0)
    )



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHtml.setOnClickListener { loadTopicTests("HTML asoslari") }
        binding.btnCss.setOnClickListener { loadTopicTests("CSS asoslari") }
        binding.btnJs.setOnClickListener { loadTopicTests("JavaScript boshlang'ich") }
        binding.btnFlexbox.setOnClickListener { loadTopicTests("Flexbox to‘liq") }
        binding.btnGrid.setOnClickListener { loadTopicTests("Grid layout asoslari") }
        binding.btnJsDom.setOnClickListener { loadTopicTests("JavaScript DOM") }
        binding.btnBootstrap.setOnClickListener { loadTopicTests("Bootstrap 5") }
        binding.btnGit.setOnClickListener { loadTopicTests("Git, GitHub asoslari") }
        binding.btnHosting.setOnClickListener { loadTopicTests("Web hosting va server") }
        binding.btnReact.setOnClickListener { loadTopicTests("React JS asoslari") }
        binding.btnVue.setOnClickListener { loadTopicTests("Vue JS asoslari") }
        binding.btnAngular.setOnClickListener { loadTopicTests("Angular asoslari") }

        showQuestion(currentQuestionIndex)


        val optionButtons = listOf(
            binding.btnOption1,
            binding.btnOption2,
            binding.btnOption3,
            binding.btnOption4
        )

        optionButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                checkAnswer(index)
            }
        }

    }

    private fun checkAnswer(selectedIndex: Int) {
        val question = currentTopicQuestions[currentQuestionIndex]

        if (selectedIndex == question.correctAnswerIndex) {
            correctAnswersCount++
            Toast.makeText(requireContext(), "To‘g‘ri javob ✅", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Noto‘g‘ri javob ❌", Toast.LENGTH_SHORT).show()
        }

        binding.root.postDelayed({
            currentQuestionIndex++

            if (currentQuestionIndex < currentTopicQuestions.size) {
                showQuestion(currentQuestionIndex)
            } else {

              showResult()
            }
        }, 1000)
    }


    private fun showQuestion(currentQuestionIndex:Int) {
        if (currentQuestionIndex < currentTopicQuestions.size) {
            val question = currentTopicQuestions[currentQuestionIndex]
            binding.textViewQuestion.text = question.question
            binding.btnOption1.text = question.options[0]
            binding.btnOption2.text = question.options[1]
            binding.btnOption3.text = question.options[2]
            binding.btnOption4.text = question.options[3]
        } else {
            Toast.makeText(requireContext(), "Test yakunlandi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun showResult() {
        binding.quizLayout.visibility = View.GONE
        binding.themeLayout.visibility = View.VISIBLE

        val totalQuestions = currentTopicQuestions.size
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Test Tugadi")
        builder.setMessage("Siz $totalQuestions ta savoldan $correctAnswersCount tasiga to'g'ri javob berdingiz!")
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.create().show()

        currentQuestionIndex = 0
        correctAnswerIndex = 0
        correctAnswersCount = 0
    }


    private fun loadTopicTests(topic: String) {

        currentTopicQuestions = when (topic) {
            "HTML asoslari" -> htmlQuestions
            "CSS asoslari" -> cssQuestions
            "JavaScript boshlang'ich" -> jsQuestions
            "Flexbox to‘liq" -> flexboxQuestions
            "Grid layout asoslari" -> gridQuestions
            "JavaScript DOM" -> jsDomQuestions
            "Bootstrap 5" -> bootstrapQuestions
            "Git, GitHub asoslari" -> gitQuestions
            "Web hosting va server" -> hostingQuestions
            "React JS asoslari" -> reactJSQuestions
            "Vue JS asoslari"-> vueJsQuestions
            "Angular asoslari" -> angularQuestions
            else -> listOf()
        }

        binding.quizLayout.visibility = View.VISIBLE
        binding.themeLayout.visibility = View.GONE

        loadQuestion()
    }

    @SuppressLint("SetTextI18n")
    private fun loadQuestion() {

        if (currentTopicQuestions.isNotEmpty()) {
            val question = currentTopicQuestions[0]
            correctAnswerIndex = question.correctAnswerIndex
            binding.textViewQuestion.text = question.question
            binding.btnOption1.text = question.options[0]
            binding.btnOption2.text = question.options[1]
            binding.btnOption3.text = question.options[2]
            binding.btnOption4.text = question.options[3]
        } else {
            Toast.makeText(requireContext(), "Testlar mavjud emas", Toast.LENGTH_SHORT).show()
        }
    }
}

data class Question(val question: String, val options: List<String>, val correctAnswerIndex: Int)
