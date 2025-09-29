const pokemonName = document.querySelector('.pokemon__name');
const pokemonNumber = document.querySelector('.pokemon__number');
const pokemonImage = document.querySelector('.pokemon__image');

const form = document.querySelector('.form');
const input = document.querySelector('.input__search');
const buttonPrev = document.querySelector('.btn-prev');
const buttonNext = document.querySelector('.btn-next');

const buttonDetails = document.querySelector('.btn-details');
const detailsContainer = document.querySelector('.pokemon__details');

let searchPokemon = 1;

// Função para buscar Pokémon na API
const fetchPokemon = async (pokemon) => {
  try {
    const response = await fetch(`http://localhost:8080/api/pokemon/${pokemon}`);
    if (response.ok) return await response.json();
  } catch (err) {
    console.error("Erro ao buscar Pokémon:", err);
  }
  return null;
};

// Renderiza Pokémon na tela
const renderPokemon = async (pokemon) => {
  pokemonName.innerHTML = 'Loading...';
  pokemonNumber.innerHTML = '';
  detailsContainer.classList.remove('active');
  detailsContainer.innerHTML = '';

  const data = await fetchPokemon(pokemon);

  if (data && data.id) {
    pokemonImage.style.display = 'block';
    pokemonName.innerHTML = data.name;
    pokemonNumber.innerHTML = data.id;
    pokemonImage.src = data.image || data.imageUrl || '';
    input.value = '';
    searchPokemon = data.id;
  } else {
    pokemonImage.style.display = 'none';
    pokemonName.innerHTML = 'Not found :c';
    pokemonNumber.innerHTML = '';
  }
};

// Evento botão detalhes
buttonDetails.addEventListener('click', async () => {
  const data = await fetchPokemon(searchPokemon);
  if (!data) return;

  detailsContainer.innerHTML = `
    <p><strong>Altura:</strong> ${data.height || 'N/A'}</p>
    <p><strong>Peso:</strong> ${data.weight || 'N/A'}</p>
    <p><strong>Tipos:</strong> ${data.types && data.types.length > 0 ? data.types.join(', ') : 'N/A'}</p>
    <p><strong>Habilidades:</strong> ${data.abilities && data.abilities.length > 0 ? data.abilities.join(', ') : 'N/A'}</p>
  `;
  detailsContainer.classList.add('active');
});

// Eventos de navegação
form.addEventListener('submit', e => { e.preventDefault(); renderPokemon(input.value.toLowerCase()); });
buttonPrev.addEventListener('click', () => { if (searchPokemon > 1) renderPokemon(--searchPokemon); });
buttonNext.addEventListener('click', () => { renderPokemon(++searchPokemon); });

// Inicializa primeiro Pokémon
renderPokemon(searchPokemon);
