export const gerarUUID = () => {
  // Gera uma string com 16 bytes aleatórios
  let d = new Date().getTime();
  const d2 = (performance && performance.now && performance.now() * 1000) || 0;
  d += d2; // Adiciona mais aleatoriedade

  return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    const r = (d + Math.random() * 16) % 16 | 0;
    d = Math.floor(d / 16);
    return (c === 'x' ? r : (r & 0x3) | 0x8).toString(16);
  });
};
