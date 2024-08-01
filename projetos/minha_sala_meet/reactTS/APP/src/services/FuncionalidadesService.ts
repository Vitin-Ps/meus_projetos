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

export const removerAcentuacoes = (texto: string): string => {
  const mapaAcentos: Record<string, string> = {
    á: 'a',
    é: 'e',
    í: 'i',
    ó: 'o',
    ú: 'u',
    ã: 'a',
    õ: 'o',
    â: 'a',
    ê: 'e',
    î: 'i',
    ô: 'o',
    û: 'u',
    à: 'a',
    è: 'e',
    ì: 'i',
    ò: 'o',
    ù: 'u',
    ä: 'a',
    ë: 'e',
    ï: 'i',
    ö: 'o',
    ü: 'u',
  };

  return texto
    .replace(/[áéíóúãõâêîôûàèìòùäëïöü]/g, (letra: string) => mapaAcentos[letra] || letra)
    .replace(/[^\w\s]/gi, '')
    .toLowerCase();
};

export const formatarData = (date: Date) => {
  const dia = String(date.getDate()).padStart(2, '0');
  const mes = String(date.getMonth() + 1).padStart(2, '0');
  const ano = date.getFullYear();
  const horas = String(date.getHours()).padStart(2, '0');
  const minutos = String(date.getMinutes()).padStart(2, '0');

  return `${dia}/${mes}/${ano} ${horas}:${minutos}`;
};
