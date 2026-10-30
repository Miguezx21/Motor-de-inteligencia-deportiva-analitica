/**
 * cascading.js — JEMPE Admin
 *
 * Cascading dropdown #1: Categoría → Disciplina (formulario atleta)
 * Cascading dropdown #2: Entrenador → Sesión → Atletas (formulario sesión)
 * Cédula input: solo dígitos, máx 10, hint de validación
 * Flash messages: auto-ocultar tras 5s
 */

document.addEventListener('DOMContentLoaded', () => {

  /* ──────────────────────────────────────────────────────────
   * Helper: poblar un <select> con un array [{id, nombre}]
   * ────────────────────────────────────────────────────────── */
  function fillSelect(selectEl, items, placeholder, selectedId) {
    selectEl.innerHTML = `<option value="">${placeholder}</option>`;
    items.forEach(item => {
      const opt = document.createElement('option');
      opt.value = item.id;
      opt.textContent = item.nombre;
      if (String(item.id) === String(selectedId)) opt.selected = true;
      selectEl.appendChild(opt);
    });
  }

  /* ──────────────────────────────────────────────────────────
   * CASCADING #1: Categoría → Disciplina
   * ────────────────────────────────────────────────────────── */
  const categoriaSelect  = document.getElementById('categoriaSelect');
  const disciplinaSelect = document.getElementById('disciplinaId');

  if (categoriaSelect && disciplinaSelect) {
    const selectedDisciplina = disciplinaSelect.dataset.selected;

    // Al cargar (edición): si ya hay categoría seleccionada, cargamos disciplinas
    if (categoriaSelect.value) {
      fetch(`/api/disciplinas?categoriaId=${categoriaSelect.value}`)
        .then(r => r.json())
        .then(data => fillSelect(disciplinaSelect, data, '— Seleccione disciplina —', selectedDisciplina))
        .catch(() => {});
    }

    categoriaSelect.addEventListener('change', () => {
      const catId = categoriaSelect.value;
      if (!catId) {
        fillSelect(disciplinaSelect, [], '— Primero elige categoría —', null);
        return;
      }
      fetch(`/api/disciplinas?categoriaId=${catId}`)
        .then(r => r.json())
        .then(data => fillSelect(disciplinaSelect, data, '— Seleccione disciplina —', null))
        .catch(() => fillSelect(disciplinaSelect, [], '— Error al cargar —', null));
    });
  }

  /* ──────────────────────────────────────────────────────────
   * CASCADING #2a: Entrenador → Sesiones (vista sesión)
   * ────────────────────────────────────────────────────────── */
  const entrenadorSelect = document.getElementById('entrenadorSelect');
  const sesionSelect     = document.getElementById('sesionSelect');
  const atletasList      = document.getElementById('atletasList');

  if (entrenadorSelect && sesionSelect) {
    entrenadorSelect.addEventListener('change', () => {
      const entId = entrenadorSelect.value;
      if (!entId) { sesionSelect.innerHTML = '<option value="">— Primero elige entrenador —</option>'; return; }
      fetch(`/api/sesiones-por-entrenador?entrenadorId=${entId}`)
        .then(r => r.json())
        .then(data => fillSelect(sesionSelect, data, '— Seleccione sesión —', null))
        .catch(() => {});
    });
  }

  /* ──────────────────────────────────────────────────────────
   * CASCADING #2b: Sesión → Atletas asignados
   * ────────────────────────────────────────────────────────── */
  if (sesionSelect && atletasList) {
    sesionSelect.addEventListener('change', () => {
      const sesId = sesionSelect.value;
      if (!sesId) { atletasList.innerHTML = ''; return; }
      fetch(`/api/atletas-por-sesion?sesionId=${sesId}`)
        .then(r => r.json())
        .then(data => {
          if (data.length === 0) {
            atletasList.innerHTML = '<li style="color:#888">Sin atletas asignados</li>';
          } else {
            atletasList.innerHTML = data.map(a => `<li>⛸️ ${a.nombre}</li>`).join('');
          }
        })
        .catch(() => { atletasList.innerHTML = ''; });
    });
  }

  /* ──────────────────────────────────────────────────────────
   * CÉDULA: solo dígitos + hint informativo
   * ────────────────────────────────────────────────────────── */
  const cedulaInput = document.getElementById('cedula');
  const cedulaHint  = document.getElementById('cedulaHint');

  if (cedulaInput && cedulaHint) {
    cedulaInput.addEventListener('input', () => {
      cedulaInput.value = cedulaInput.value.replace(/\D/g, '').slice(0, 10);
      const len = cedulaInput.value.length;
      if (len === 0) {
        cedulaHint.textContent = '';
        cedulaHint.style.color = '';
      } else if (len < 10) {
        cedulaHint.textContent = `${len}/10 dígitos — La validación Módulo 10 se ejecuta en el servidor`;
        cedulaHint.style.color = '#e8a020';
      } else {
        cedulaHint.textContent = '✓ 10 dígitos ingresados — Enviando al servidor para validación Módulo 10';
        cedulaHint.style.color = '#16a34a';
      }
    });
  }

  /* ──────────────────────────────────────────────────────────
   * Flash messages: auto-ocultar
   * ────────────────────────────────────────────────────────── */
  document.querySelectorAll('.alert').forEach(alert => {
    setTimeout(() => {
      alert.style.transition = 'opacity .5s';
      alert.style.opacity = '0';
      setTimeout(() => alert.remove(), 500);
    }, 5000);
  });

});
