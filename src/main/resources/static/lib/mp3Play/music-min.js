var music = document.getElementById("music");
var starbg = document.getElementsByClassName("icon-star");
var mp = music.paused;
var bol = document.getElementsByClassName("bol");
var ytext = document.getElementsByClassName("ytext");
var yn = document.getElementById("yn");
var son = 0;
var mpre = '/blog/mp3/';
var msuf = '.mp3';
var ipre = '/blog/images/songImg/';
var isuf = '.jpg';
var musicsNameList = [
    "无名氏",
    "长大就好",
    "自私",
    "NEXT TO YOU",
    "Otokaze",
    "Remember",
    "Where are you"];
var musicsList = [
    mpre + "排骨教主-无名氏" + msuf,
    mpre + "司南 - 长大就好" + msuf,
    mpre + "奇然 - 自私" + msuf,
    mpre + "NEXT TO YOU" + msuf,
    mpre + "Otokaze - 夏恋" + msuf,
    mpre + "Remember" + msuf,
    mpre + "Where are you" + msuf
];

var musicImgs = [ipre + "排骨教主-无名氏" + isuf,
    ipre + "长大就好-司南" + isuf,
    ipre +"奇然-自私" + isuf,
    ipre +"NEXT TO YOU" + isuf,
    ipre +"Otokaze" + isuf,
    ipre +"Remember" + isuf,
    ipre +"Where are you" + isuf];
var ls = document.getElementById("ls");

for (var i = 0; i < musicsNameList.length; i++) {
    var str = document.createElement("div");
    str.append(musicsNameList[i]);
    ls.appendChild(str);
}

function star() {
    if (music.paused) {
        music.play();
        starbg[0].style.background = "url('/blog/images/musicImg/zt.png') no-repeat center";
        starbg[0].style.backgroundSize = "99% 99%";
        rotates()
    } else {
        music.pause();
        starbg[0].style.background = "url('/blog/images/musicImg/actions.png') no-repeat center";
        starbg[0].style.backgroundSize = "99% 99%";
        clearInterval(timer)
    }
}

function cours() {
    var x = parseInt(this.event.offsetY);
    console.log("x=" + x);
    if (x >= 40) {
        x = 40;
        music.volume = parseFloat(x / 40);
        bol[0].style.top = "40px";
        ytext[0].innerHTML = 100 + "";
        yn.style.height = x + "px"
    } else if (x <= 0) {
        x = 0;
        music.volume = 0;
        bol[0].style.top = "0px";
        ytext[0].innerHTML = 0 + "";
        yn.style.height = x + 2 + "px"
    } else {
        bol[0].style.top = x + 2 + "px";
        music.volume = parseFloat(x / 40);
        ytext[0].innerHTML = parseInt(((x / 40) * 100)) + "";
        yn.style.height = x + 4 + "px"
    }
}

var deg = 0;
var nextDeg = 0;

function rotates() {
    if (nextDeg !== 0) {
        deg = nextDeg
    }
    var imgs = document.getElementsByClassName("imgs");
    var flag = 1;
    timer = setInterval(function () {
            imgs[0].style.transform = "rotate(" + deg + "deg)";
            deg += 1;
            if (deg > 360) {
                deg = 0
            }
            nextDeg = deg
        },
        50)
}

var flag = true;

function song() {
    var bg = document.getElementsByClassName("icon-yin")[0];
    if (flag) {
        son = music.volume;
        music.volume = 0;
        bg.style.backgroundImage = "url('/images/musicImg/欢迎图.png')";
        flag = false
    } else {
        music.volume = son;
        bg.style.backgroundImage = "url('/images/musicImg/欢迎图.png')";
        flag = true
    }
}

var num = 0;
var bgImg = document.getElementsByClassName("imgs")[0];

function showName() {
    console.log(musicsList[num]);
    var name = musicsList[num].substring(10, musicsList[num].length - 4);
    console.log(name);
    $("#songName").text(name)
}

function upMusic() {
    music.pause();
    if (num === 0) {
        num = musicsList.length - 1
    } else {
        num--
    }
    bgImg.style.backgroundImage = "url(\'" + musicImgs[num] + "\')";
    music.src = musicsList[num];
    console.log(num);
    console.log(musicsList[num]);
    clearInterval(timer);
    star();
    showName();

}

function dowMusic() {
    music.pause();
    if (num === (musicsList.length - 1)) {
        num = 0
    } else {
        num++
    }
    bgImg.style.backgroundImage = "url(\'" + musicImgs[num] + "\')";
    showName();
    music.src = musicsList[num];
    console.log(num);
    clearInterval(timer);
    star();

}