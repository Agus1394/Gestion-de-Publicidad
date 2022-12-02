// EFECTO MAQUINA DE ESCRIBIR

const efectoTexto = document.getElementById("typewriter")
const typewriter = new Typewriter(efectoTexto,{
    loop:true,
    delay:50
});
typewriter.typeString("AGENCIA UNICA PARA TU MARKETING DIGITAL").pauseFor(2000).start();
