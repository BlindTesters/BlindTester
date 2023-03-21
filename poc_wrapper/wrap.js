// wrapper function that will return the provided function name,
// the provided arguments and the result of the provided function.
const wrap = (f) => {
    return (...args) => {
        return {
            'function_name': f.name,
            'arguments': args,
            'result': f(...args)
        }
    }
}

module.exports = { wrap };
