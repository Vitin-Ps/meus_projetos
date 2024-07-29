
import api from './api';

// export async function cadastrarUsuario(usuario: Usuario) {
//   if (!usuario) {
//     return;
//   }

//   const usuarioJSON: string = JSON.stringify(usuario);

//   try {
//     const res = await api.post('/login/cad/user', usuarioJSON, {
//       headers: { 'Content-Type': 'application/json' },
//     });
//     return res.data;
//   } catch (error) {
//     console.log('Error: ', error);
//     return null;
//   }
// }

export async function listarGruposPorUser(id: number) {
  if (!id) {
    return;
  }

  try {
    const res = await api.get(`/group/user/${id}`);
    return res.data;
  } catch (error) {
    console.log('Error: ', error);
    return null;
  }
}

export async function detalharGrupo(id: number) {
  if (!id) {
    return;
  }

  try {
    const res = await api.get(`/group/${id}`);
    return res.data;
  } catch (error) {
    console.log('Error: ', error);
    return null;
  }
}
