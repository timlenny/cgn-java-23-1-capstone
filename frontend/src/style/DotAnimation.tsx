import React, {useEffect} from 'react';
import '../style/AuthPageStyle.css';

class Dot {
    size: number;
    dx: number;
    dy: number;
    px: number;
    py: number;

    constructor(private width: number, private height: number) {
        const angle = Math.floor(Math.random() * 360);

        this.size = 6;
        this.dx = Math.cos(angle) * 0.25;
        this.dy = Math.sin(angle) * 0.25;
        this.px = Math.random() * width;
        this.py = Math.random() * height;
    }

    update() {
        this.bounds();

        this.px += this.dx;
        this.py += this.dy;
    }

    draw(scene: CanvasRenderingContext2D) {
        scene.beginPath();
        scene.arc(this.px, this.py, this.size, 0, Math.PI * 2);
        scene.closePath();
        scene.fillStyle = '#8284FF';
        scene.fill();

        this.connect(scene);
    }

    connect(scene: CanvasRenderingContext2D) {
        const nearby = (this.width + this.height) * 0.1;

        dots.forEach(dot => {
            const distance = this.distance(dot);

            if (distance > nearby) return;

            const opacity = 1 - distance / nearby - 0.2;
            scene.beginPath();
            scene.lineWidth = 1;
            scene.strokeStyle = `rgba(255,255,255, ${opacity})`;
            scene.moveTo(this.px, this.py);
            scene.lineTo(dot.px, dot.py);
            scene.stroke();
        });
    }

    bounds() {
        if (this.px < 0 || this.px > this.width) this.dx *= -1;

        if (this.py < 0 || this.py > this.height) this.dy *= -1;
    }

    distance(dot: Dot) {
        const distX = this.px - dot.px;
        const distY = this.py - dot.py;

        return Math.sqrt(distX * distX + distY * distY);
    }
}

let dots: Dot[];

const createDots = (count: number, width: number, height: number): Dot[] => {
    return Array.from({length: count}, () => new Dot(width, height));
};

const handleResize = (canvas: HTMLCanvasElement) => {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
};

const draw = (scene: CanvasRenderingContext2D, width: number, height: number) => {
    scene.clearRect(0, 0, width, height);

    dots.forEach(particle => {
        particle.update();
        particle.draw(scene);
    });

    requestAnimationFrame(() => draw(scene, width, height));
};

const DotAnimation: React.FC = () => {
    useEffect(() => {
        const canvas = document.getElementById('canvas') as HTMLCanvasElement;
        const scene = canvas && canvas.getContext('2d');
        if (!canvas || !scene) return;

        let width = (canvas.width = window.innerWidth);
        let height = (canvas.height = window.innerHeight);

        dots = createDots(75, width, height);
        draw(scene, width, height);

        const resizeListener = () => {
            handleResize(canvas);
            width = canvas.width;
            height = canvas.height;
        };

        window.addEventListener('resize', resizeListener);

        return () => {
            window.removeEventListener('resize', resizeListener);
        };
    }, []);

    return (
        <div className="dot-animation">
            <canvas id="canvas"></canvas>
        </div>
    );

};

export default DotAnimation;