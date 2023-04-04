import {gsap} from 'gsap';

function setElementsTransformOrigin() {
    gsap.set("#shadow", {transformOrigin: "15px 8px"});
    gsap.set("#tree", {transformOrigin: "154px bottom"});
    gsap.set("#leaf-rb", {transformOrigin: "left bottom"});
    gsap.set("#leaf-rm", {transformOrigin: "left bottom"});
    gsap.set("#leaf-lb", {transformOrigin: "right bottom"});
    gsap.set("#leaf-lm", {transformOrigin: "right bottom"});
    gsap.set("#leaf-top", {transformOrigin: "center bottom"});
    gsap.set("#leaf-rb g", {transformOrigin: "left 60px"});
    gsap.set("#leaf-rm g", {transformOrigin: "22px 140px"});
    gsap.set("#leaf-lb g", {transformOrigin: "right 56px"});
    gsap.set("#leaf-lm g", {transformOrigin: "106px bottom"});
}

function resetElements() {
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

export function setup() {

    setElementsTransformOrigin();

    gsap.set("#shadow", {
        scale: 0,
        transformOrigin: "15px 8px"
    });
    gsap.set("#tree", {
        scale: 0,
        transformOrigin: "154px bottom"
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

export function animate() {

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

    if (!shadow || !tree || !leafRb || !leafRm || !leafLb || !leafLm || !leafTop || !leafLbG || !leafLmG || !leafRbG || !leafRmG) {
        return;
    }

    const tl = gsap.timeline({
        delay: 0,
    });

    tl.to("#shadow", {scale: 1, duration: 2}, 0)
        .to("#tree", {scale: 1, duration: 2}, 0)
        .to("#leaf-rb", {scale: 1, rotation: '0cw', y: 0, delay: 0.35, duration: 2}, 0)
        .to("#leaf-rm", {scale: 1, rotation: '0cw', y: 0, delay: 0.35, duration: 2}, 0)
        .to("#leaf-lb", {scale: 1, rotation: '0cw', y: 0, delay: 0.35, duration: 2}, 0)
        .to("#leaf-lm", {scale: 1, rotation: '0cw', y: 0, delay: 0.35, duration: 2}, 0)
        .to("#leaf-top", {scale: 1, delay: 0.35, duration: 2.5}, 0)
        .to("#leaf-lb g", {scale: 1, delay: 0.5, duration: 2.25}, 0)
        .to("#leaf-lm g", {scale: 1, delay: 0.6, duration: 2.25}, 0)
        .to("#leaf-rb g", {scale: 1, delay: 0.5, duration: 2.25}, 0)
        .to("#leaf-rm g", {scale: 1, delay: 0.6, duration: 2.25}, 0);
    return tl;
}

export function stopAndReset() {
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

    if (!shadow || !tree || !leafRb || !leafRm || !leafLb || !leafLm || !leafTop || !leafLbG || !leafLmG || !leafRbG || !leafRmG) {
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
    resetElements()
}

export function playAgain() {
    stopAndReset();
    setup();
    animate();
}

stopAndReset();
setup();
window.onload = animate;
