interface Props {
    date: string;
}

const FormatDateLocal = (props: Props): string => {
    const formattedDateToLocal = new Date(props.date).toLocaleString('default', {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        second: 'numeric'
    });
    return formattedDateToLocal.split(' ').slice(0, -2).join(' ');
};
export default FormatDateLocal;
