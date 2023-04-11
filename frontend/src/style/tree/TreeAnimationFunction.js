import {gsap} from 'gsap';

function getElements() {

    const shadow = document.querySelector("#shadow");
    const tree = document.querySelector("#tree");
    const leafRb = document.querySelector("#leaf-rb");
    const leafRm = document.querySelector("#leaf-rm");
    const leafLb = document.querySelector("#leaf-lb");
    const leafLm = document.querySelector("#leaf-lm");
    const leafTop = document.querySelector("#leaf-top");
    const leafLbG = document.querySelector("#leaf-lb g");
    const leafLmG = document.querySelector("#leaf-lm g");
    const leafRbG = document.querySelector("#leaf-rb g");
    const leafRmG = document.querySelector("#leaf-rm g");

    if (
        !shadow || !tree || !leafRb || !leafRm || !leafLb || !leafLm ||
        !leafTop || !leafLbG || !leafLmG || !leafRbG || !leafRmG
    ) {
        return null;
    }

    return {
        shadow,
        tree,
        leafRb,
        leafRm,
        leafLb,
        leafLm,
        leafTop,
        leafLbG,
        leafLmG,
        leafRbG,
        leafRmG,
    };
}


export function setup() {

    const elements = getElements();
    if (!elements) {
        return;
    }


    gsap.set("#shadow", {
        scale: 0,
        transformOrigin: "15px 8px"
    });
    gsap.set("#tree", {
        scale: 0,
        transformOrigin: "154px bottom",
    });
    gsap.set("#leaf-rb", {
        scale: 0,
        rotation: '-60cw',
        y: -15,
        transformOrigin: "left bottom"
    });
    gsap.set("#leaf-rm", {
        scale: 0,
        rotation: '-50cw',
        y: 30,
        transformOrigin: "left bottom"
    });
    gsap.set("#leaf-lb", {
        scale: 0,
        rotation: '60cw',
        y: -80,
        transformOrigin: "right bottom"
    });
    gsap.set("#leaf-lm", {
        scale: 0,
        rotation: '40cw',
        y: -90,
        transformOrigin: "right bottom"
    });
    gsap.set("#leaf-top", {
        scale: 0,
        transformOrigin: "center bottom"
    });
    gsap.set("#leaf-rb g", {
        scale: 0,
        transformOrigin: "left 60px"
    });
    gsap.set("#leaf-rm g", {
        scale: 0,
        transformOrigin: "22px 140px"
    });
    gsap.set("#leaf-lb g", {
        scale: 0,
        transformOrigin: "right 56px"
    });
    gsap.set("#leaf-lm g", {
        scale: 0,
        transformOrigin: "106px bottom"
    });
}

export function animateLarge() {


    const elements = getElements();
    if (!elements) {
        return;
    }

    const tl = gsap.timeline({
        delay: 0,
    });

    tl.to("#shadow", {scale: 1, duration: 2}, 0)
        .to("#tree", {scale: 1, duration: 2, opacity: 1}, 0)
        .to("#leaf-rb3", {scale: 1, rotation: '0cw', y: 0, delay: 0.35, duration: 2}, 0)
        .to("#leaf-rm3", {scale: 1, rotation: '0cw', y: 0, delay: 0.35, duration: 2}, 0)
        .to("#leaf-lb3", {scale: 1, rotation: '0cw', y: 0, delay: 0.35, duration: 2}, 0)
        .to("#leaf-lm3", {scale: 1, rotation: '0cw', y: 0, delay: 0.35, duration: 2}, 0)
        .to("#leaf-top3", {scale: 1, delay: 0.35, duration: 2.5}, 0)
        .to("#leaf-lb g", {scale: 1, delay: 0.5, duration: 2.25}, 0)
        .to("#leaf-lm g", {scale: 1, delay: 0.6, duration: 2.25}, 0)
        .to("#leaf-rb g", {scale: 1, delay: 0.5, duration: 2.25}, 0)
        .to("#leaf-rm g", {scale: 1, delay: 0.6, duration: 2.25}, 0);
    return tl;
}

export function animateMedium() {

    const elements2 = getElements();
    if (!elements2) {
        return;
    }


    const tl2 = gsap.timeline({
        delay: 0,
    });

    tl2.to("#shadow", 2, {scale: 1}, 0)
        .to("#tree", 2, {scale: 1}, 0)
        .to("#leaf-rb2", 2, {scale: 1, rotation: '0cw', y: 0, delay: 0.35}, 0)
        .to("#leaf-rm2", 2, {scale: 1, rotation: '0cw', y: 0, delay: 0.35}, 0)
        .to("#leaf-lb2", 2, {scale: 1, rotation: '0cw', y: 0, delay: 0.35}, 0)
        .to("#leaf-lm2", 2, {scale: 1, rotation: '0cw', y: 0, delay: 0.35}, 0)
        .to("#leaf-top2", 0, {scale: 0, delay: 0.35}, 0)
        .to("#leaf-lb g", 2.25, {scale: 1, delay: 0.5}, 0)
        .to("#leaf-lm g", 0, {scale: 0, delay: 0.6}, 0)
        .to("#leaf-rb g", 2.25, {scale: 1, delay: 0.5}, 0)
        .to("#leaf-rm g", 2.25, {scale: 1, delay: 0.6}, 0);

    return tl2;
}

export function animateSmall() {

    const elements = getElements();
    if (!elements) {
        return;
    }

    const tl = gsap.timeline({
        delay: 0,
    });

    tl.to("#shadow", 2, {scale: 1}, 0)
        .to("#tree", 2, {scale: 1}, 0)
        .to("#leaf-rb", 2, {scale: 1, rotation: '0cw', y: 0, delay: 0.35}, 0)
        .to("#leaf-rm", 2, {scale: 1, rotation: '0cw', y: 0, delay: 0.35}, 0)
        .to("#leaf-lb", 2, {scale: 1, rotation: '0cw', y: 0, delay: 0.35}, 0)
        .to("#leaf-lm", 2, {scale: 1, rotation: '0cw', y: 0, delay: 0.35}, 0)
        .to("#leaf-top", 0, {scale: 0, delay: 0.35}, 0)
        .to("#leaf-lb g", 2.25, {scale: 1, delay: 0.5}, 0)
        .to("#leaf-lm g", 0, {scale: 0, delay: 0.6}, 0)
        .to("#leaf-rb g", 2.25, {scale: 1, delay: 0.5}, 0)
        .to("#leaf-rm g", 0, {scale: 0, delay: 0.6}, 0);


    return tl;
}

export function stopAndReset() {
    const elements = getElements();
    if (!elements) {
        return;
    }

    gsap.killTweensOf("#tree");
    gsap.killTweensOf("#shadow");
    gsap.killTweensOf("#leaf-top");
    gsap.killTweensOf("#leaf-rb");
    gsap.killTweensOf("#leaf-rm");
    gsap.killTweensOf("#leaf-lb");
    gsap.killTweensOf("#leaf-lm");
    gsap.killTweensOf("#leaf-rb g");
    gsap.killTweensOf("#leaf-rm g");
    gsap.killTweensOf("#leaf-lb g");
    gsap.killTweensOf("#leaf-lm g");
    gsap.set("#tree", {clearProps: "all"});
    gsap.set("#shadow", {clearProps: "all"});
    gsap.set("#leaf-rb", {clearProps: "all"});
    gsap.set("#leaf-rm", {clearProps: "all"});
    gsap.set("#leaf-lb", {clearProps: "all"});
    gsap.set("#leaf-lm", {clearProps: "all"});
    gsap.set("#leaf-top", {clearProps: "all"});
    gsap.set("#leaf-rb g", {clearProps: "all"});
    gsap.set("#leaf-rm g", {clearProps: "all"});
    gsap.set("#leaf-lb g", {clearProps: "all"});
    gsap.set("#leaf-lm g", {clearProps: "all"});
}

export function playAgain(props) {
    stopAndReset()
    setup()
    if (props === 1) {
        animateSmall()
    } else if (props === 2) {
        animateMedium()
    } else {
        animateLarge()
    }
}

stopAndReset();
setup();
