document.addEventListener('DOMContentLoaded', function() {
    const images = [
        '../imagens/imagem2.jpg',
        '../imagens/imagem3.jpg',
        '../imagens/imagem4.jpg',
        '../imagens/imagem5.jpg',
        '../imagens/imagem6.jpg',
        '../imagens/imagem7.jpg',
    ];

    // Embaralha o array de imagens
    const shuffledImages = images.sort(() => Math.random() - 0.5);

    // Aplica as imagens aos slides
    const slides = document.querySelectorAll('.slide');
    slides.forEach((slide, index) => {
        if (shuffledImages[index]) {
            slide.style.backgroundImage = `url('${shuffledImages[index]}')`;
            slide.style.animationDelay = `${index * 5}s`;
        }
    });
});